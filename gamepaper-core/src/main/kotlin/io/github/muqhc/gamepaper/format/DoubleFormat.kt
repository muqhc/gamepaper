package io.github.muqhc.gamepaper.format

import io.github.muqhc.skollobleparser.Element

object DoubleFormat: FormatSingleElement<Double> {
    override fun construct(target: Element) = target.strings[0].toDouble()
}