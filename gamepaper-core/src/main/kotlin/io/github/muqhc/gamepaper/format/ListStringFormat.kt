package io.github.muqhc.gamepaper.format

import io.github.muqhc.skollobleparser.Element

object ListStringFormat: FormatSingleElement<List<String>> {
    override fun construct(target: Element) = target.strings
}