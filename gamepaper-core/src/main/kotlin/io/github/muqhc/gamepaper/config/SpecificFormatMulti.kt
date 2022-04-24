package io.github.muqhc.gamepaper.config

import io.github.muqhc.gamepaper.format.FormatMultiElement
import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
annotation class SpecificFormatMulti<T>(val format: KClass<out FormatMultiElement<T>>)
