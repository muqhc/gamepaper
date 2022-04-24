package io.github.muqhc.gamepaper.format

import io.github.muqhc.skollobleparser.Element

object ListDoubleFormat: FormatSingleElement<List<Double>> {
    override fun construct(target: Element) = target.strings.map { it.toDouble() }
}