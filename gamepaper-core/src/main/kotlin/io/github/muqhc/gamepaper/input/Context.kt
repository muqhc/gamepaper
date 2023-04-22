package io.github.muqhc.gamepaper.input

import kotlin.reflect.KClass

interface Context<T : Any> {
    val clazz: KClass<out T>
    fun suggest(): List<String>
    fun checkIsValid(arg: String): Boolean
    fun convert(arg: String): T
}