package io.github.muqhc.gamepaper.game

import io.github.muqhc.gamepaper.config.GameConfig
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class ConfigureWith(val target: KClass<out GameConfig>)
