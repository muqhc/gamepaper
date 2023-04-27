package io.github.muqhc.gamepaper.input

import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class SpecificContext(val parser: KClass<out Context<*>>)
