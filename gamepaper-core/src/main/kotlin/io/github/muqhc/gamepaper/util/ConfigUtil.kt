package io.github.muqhc.gamepaper.util

import io.github.muqhc.gamepaper.config.*
import io.github.muqhc.gamepaper.exception.ConfigEntryNotFoundException
import io.github.muqhc.gamepaper.exception.FormatterNotFoundException
import io.github.muqhc.gamepaper.format.*
import io.github.muqhc.skollobleparser.Element
import io.github.muqhc.skollobleparser.SkollobleParser
import java.io.File
import java.util.jar.JarFile
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.typeOf

fun GameConfig.Companion.skollobleOfJar(jarFile: File): Element {
    val jar = JarFile(jarFile)
    val gameConfEntry = (jar.getJarEntry("game.skolloble") ?: jar.getJarEntry("game.skble"))!!
    val gameConfText = jar.getInputStream(gameConfEntry).readAllBytes().decodeToString()
    return SkollobleParser(gameConfText).rootElement
}

fun GameConfig.Companion.skollobleOfFile(file: File): Element {
    return SkollobleParser(file.readBytes().decodeToString()).rootElement
}

fun KProperty1<out GameConfig,*>.getSingleFormatter(): FormatSingleElement<*> {
    val formatNotation = annotations.find { it is SpecificFormatSingle<*> } as? SpecificFormatSingle<*>
    if (formatNotation != null) return formatNotation.format.objectInstance!!
    return when (returnType) {
        typeOf<String>() -> StringFormat
        typeOf<Int>() -> IntFormat
        typeOf<Double>() -> DoubleFormat
        typeOf<List<String>>() -> StringFormat
        typeOf<List<Int>>() -> IntFormat
        typeOf<List<Double>>() -> DoubleFormat
        else -> throw FormatterNotFoundException("Could not find single formatter for $this")
    }
}

fun KProperty1<out GameConfig,*>.getMultiFormatter(): FormatMultiElement<*> {
    val formatNotation = annotations.find { it is SpecificFormatMulti<*> } as? SpecificFormatMulti<*>
    if (formatNotation != null) return formatNotation.format.objectInstance!!
    throw FormatterNotFoundException("Could not find multi formatter for $this")
}

fun KProperty1<out GameConfig,*>.getFormatter(): Format<*,*> =
    if (annotations.find { it is SpecificFormatMulti<*> } == null) getSingleFormatter()
    else getMultiFormatter()

fun GameConfig.Companion.valueMapOfProps(props: List<KProperty1<out GameConfig,*>>, root: Element) =
    props.associateWith { prop ->
        val formatter = prop.getFormatter()
        if (root.children.find { it.name == prop.name } == null)
            if (prop.returnType.isMarkedNullable) null
            else throw ConfigEntryNotFoundException("Could not find config entry '${prop.name}' ($prop)")
        else
            if (formatter is FormatSingleElement<*>) formatter.constructOnValid(root.children.find { it.name == prop.name }!!)
            else (formatter as FormatMultiElement<*>).constructOnValid(root.children.filter { it.name == prop.name }.takeIf { it.isNotEmpty() }!!)
    }

fun GameConfig.Companion.valueMapOfOnResource(kClass: KClass<out GameConfig>, root: Element) =
    valueMapOfProps(kClass.memberProperties
        .filter { it.annotations.any { it is GameConfigEntry } }
        .filter { it.annotations.contains(ResourceConfig()) },root)

fun GameConfig.Companion.valueMapOfOutOfResource(kClass: KClass<out GameConfig>, root: Element) =
    valueMapOfProps(kClass.memberProperties
        .filter { it.annotations.any { it is GameConfigEntry } }
        .filterNot { it.annotations.contains(ResourceConfig()) },root)
