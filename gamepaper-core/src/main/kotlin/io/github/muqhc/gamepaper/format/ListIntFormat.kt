package io.github.muqhc.gamepaper.format

import io.github.muqhc.skollobleparser.Element

object ListIntFormat: FormatSingleElement<List<Int>> {
    override fun construct(target: Element) = target.strings.map { it.toInt() }
}