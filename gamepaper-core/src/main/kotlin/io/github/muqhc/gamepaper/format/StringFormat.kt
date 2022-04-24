package io.github.muqhc.gamepaper.format

import io.github.muqhc.skollobleparser.Element

object StringFormat: FormatSingleElement<String> {
    override fun construct(target: Element) = target.strings[0]
}