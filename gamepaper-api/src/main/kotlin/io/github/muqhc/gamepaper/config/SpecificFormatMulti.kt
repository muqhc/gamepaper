package io.github.muqhc.gamepaper.config

import io.github.muqhc.gamepaper.format.FormatMultiElement
import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
annotation class SpecificFormatMulti(val format: KClass<out FormatMultiElement<*>>)
