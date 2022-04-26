package io.github.muqhc.gamepaper.format

import io.github.muqhc.gamepaper.exception.InvalidConfigException
import io.github.muqhc.skollobleparser.Element

object DoubleFormat: FormatSingleElement<Double> {
    override fun checkIsValid(target: Element) {
        if (target.attribution.isNotEmpty())
            throw InvalidConfigException("it cannot contains attributes")
        target.strings[0].runCatching {
            toDouble()
        }.onFailure {
            throw InvalidConfigException("'${target.strings[0]}' is not double",it)
        }
    }

    override fun construct(target: Element) = target.strings[0].toDouble()
}