package io.github.muqhc.gamepaper.config

import io.github.muqhc.gamepaper.util.getFormatter
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

fun generateConfig(configKClass: KClass<out GameConfig>) =
    configKClass.memberProperties
        .filter { it.annotations.any { it is GameConfigEntry } }
        .filter { !it.annotations.any { it is ResourceConfig } }
        .map {
            ";${(it.annotations.find { it is GameConfigEntry } as GameConfigEntry).comment};\n" +
                    "${it.name} ".plus(it.annotations
                        .find { it is DefaultGeneratingTextValue }
                        ?.let {
                            (it as DefaultGeneratingTextValue).valueText.trimIndent()
                        }
                        ?: it.getFormatter().defaultGenText
                    )
        }
        .joinToString("\n\n")
        .let { "game /\n\n$it" }