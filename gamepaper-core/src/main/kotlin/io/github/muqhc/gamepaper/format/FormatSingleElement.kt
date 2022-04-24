package io.github.muqhc.gamepaper.format

import io.github.muqhc.skollobleparser.Element

fun interface FormatSingleElement<TargetType>: Format<TargetType,Element> {
    override fun construct(target: Element): TargetType
}