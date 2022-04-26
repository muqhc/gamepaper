package io.github.muqhc.gamepaper.format

import io.github.muqhc.gamepaper.exception.InvalidConfigException
import io.github.muqhc.skollobleparser.Element

object IntFormat: FormatSingleElement<Int> {
    override fun checkIsValid(target: Element) {
        if (target.attribution.isNotEmpty())
            throw InvalidConfigException("it cannot contains attributes")
        target.strings[0].runCatching {
            toInt()
        }.onFailure {
            throw InvalidConfigException("'${target.strings[0]}' is not integer",it)
        }
    }
    override fun construct(target: Element) = target.strings[0].toInt()
}