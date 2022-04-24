package io.github.muqhc.gamepaper.format

import io.github.muqhc.skollobleparser.Element

object IntFormat: FormatSingleElement<Int> {
    override fun construct(target: Element) = target.strings[0].toInt()
}