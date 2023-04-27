package io.github.muqhc.gamepaper.config

import io.github.muqhc.gamepaper.format.FormatSingleElement
import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
annotation class SpecificFormatSingle(val format: KClass<out FormatSingleElement<*>>)
