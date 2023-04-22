package io.github.muqhc.gamepaper.format

import io.github.muqhc.gamepaper.exception.InvalidConfigException
import io.github.muqhc.skollobleparser.Element

object IntListFormat: FormatSingleElement<List<Int>> {
    override val defaultGenText: String = """
        {
            `1`
            `2`
            `3`
        }
    """.trimIndent()

    override fun checkIsValid(target: Element) {
        if (target.attribution.isNotEmpty())
            throw InvalidConfigException("it cannot contains attributes")
        target.strings.forEach {
            it.runCatching {
                toDouble()
            }.onFailure {
                throw InvalidConfigException("'${target.strings[0]}' is not integer", it)
            }
        }
    }
    override fun construct(target: Element) = target.strings.map { it.toInt() }
}