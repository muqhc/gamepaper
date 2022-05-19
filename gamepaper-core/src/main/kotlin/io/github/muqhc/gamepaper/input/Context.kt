package io.github.muqhc.gamepaper.input

interface Context<T> {
    fun suggest(): List<String>
    fun checkIsValid(arg: String): Boolean
    fun convert(arg: String): T
}