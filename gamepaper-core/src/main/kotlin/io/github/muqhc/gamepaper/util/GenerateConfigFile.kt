package io.github.muqhc.gamepaper.util

import io.github.muqhc.gamepaper.config.DefaultGeneratingTextValue
import io.github.muqhc.gamepaper.config.GameConfig
import io.github.muqhc.gamepaper.config.GameConfigEntry
import io.github.muqhc.gamepaper.config.ResourceConfig
import io.github.muqhc.gamepaper.game.GameInfo
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

fun generateConfig(configKClass: KClass<out GameConfig>, info: GameInfo) =
    configKClass.memberProperties
        .filter { it.annotations.any { it is GameConfigEntry } }
        .filter { !it.annotations.any { it is ResourceConfig } }
        .map {
            "*${(it.annotations.find { it is GameConfigEntry } as GameConfigEntry).comment}*\n" +
                    "${it.name} ".plus(it.annotations
                        .find { it is DefaultGeneratingTextValue }
                        ?.let {
                            (it as DefaultGeneratingTextValue).valueText.trimIndent()
                        }
                        ?: it.getFormatter().defaultGenText
                    )
        }
        .joinToString("\n\n")
        .let { "game: id`${info.id}` /\n\n$it" }