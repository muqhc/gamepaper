package io.github.muqhc.gamepaper.format

import io.github.muqhc.skollobleparser.Element

fun interface FormatMultiElement<TargetType>: Format<TargetType,List<Element>> {
    override fun construct(target: List<Element>): TargetType
}