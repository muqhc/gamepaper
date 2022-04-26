package io.github.muqhc.gamepaper.format

import io.github.muqhc.gamepaper.exception.InvalidConfigException
import io.github.muqhc.skollobleparser.Element

object StringFormat: FormatSingleElement<String> {
    override fun checkIsValid(target: Element) {
        if (target.attribution.isNotEmpty())
            throw InvalidConfigException("it cannot contains attributes")
    }

    override fun construct(target: Element) = target.strings[0]
}