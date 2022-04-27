package io.github.muqhc.gamepaper.format

import io.github.muqhc.gamepaper.exception.InvalidConfigException
import io.github.muqhc.skollobleparser.Element

object ListStringFormat: FormatSingleElement<List<String>> {
    override val defaultGenText: String = """
        {
            `first`
            `second`
            `third`
        }
    """.trimIndent()

    override fun checkIsValid(target: Element) {
        if (target.attribution.isNotEmpty())
            throw InvalidConfigException("it cannot contains attributes")
    }

    override fun construct(target: Element) = target.strings
}