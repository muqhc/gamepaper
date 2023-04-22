package io.github.muqhc.gamepaper

import io.github.muqhc.gamepaper.config.GameConfig
import io.github.muqhc.gamepaper.util.generateConfig
import io.github.muqhc.gamepaper.game.GameInfo
import io.github.muqhc.gamepaper.input.Context
import io.github.muqhc.gamepaper.input.SpecificContext
import io.github.muqhc.gamepaper.proxy.implementGameConfig
import io.github.muqhc.gamepaper.util.skollobleOfFile
import io.github.muqhc.gamepaper.util.skollobleOfJar
import io.github.muqhc.gamepaper.util.valueMapOfOnResource
import io.github.muqhc.gamepaper.util.valueMapOfOutOfResource
import java.io.File
import java.lang.reflect.Type
import kotlin.reflect.KType
import kotlin.reflect.jvm.javaGetter
import kotlin.reflect.typeOf

class GamePack(val jarFile: File, val configClass: Class<out GameConfig>, val gameClass: Class<out Game<*>>, val info: GameInfo) {
    lateinit var configPropValueMap: Map<String,Any?>
    lateinit var configProxy: GameConfig

    var defaultContextKit: Map<KType,Context<*>> = mapOf()

    val gameConstructorContexts =  try {
        gameClass.kotlin.constructors.map { c ->
            c.parameters.map {
                it.annotations.filterIsInstance<SpecificContext<*>>()
                    .firstOrNull()?.parser?.objectInstance
                    ?: defaultContextKit.getOrElse(it.type) {
                        error(
                            "Could not find right context for the parameter (${it.name}: ${it.type}). " +
                                "Please annotate context with 'SpecificContext<T>' annotation"
                        )
                    }
            }
        }
    } catch (e: Exception) { null }

    fun createGameConfigProxy(configFile: File) {
        val skbleOfFile = GameConfig.skollobleOfFile(configFile)
        val skbleOfJar = GameConfig.skollobleOfJar(jarFile)

        configPropValueMap = GameConfig.valueMapOfOnResource(configClass.kotlin,skbleOfJar)
            .plus(GameConfig.valueMapOfOutOfResource(configClass.kotlin,skbleOfFile))
            .mapKeys { it.key.javaGetter!!.name }

        configProxy = implementGameConfig(configClass,configPropValueMap)
    }

    fun createConfigFile(path: String): File {
        return createConfigFile(File(path))
    }

    fun createConfigFile(dir: File): File {
        val file = File(dir,"${info.id}.skolloble")
        file.writeText(generateConfig(configClass.kotlin,info))
        file.createNewFile()
        return file
    }

    fun newGameInstance(args: List<Any>) =
        gameClass.getConstructor(*args.map { it.javaClass }.toTypedArray()).newInstance(args)
            .also { it.configProxy = configProxy }
}