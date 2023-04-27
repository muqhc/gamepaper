package io.github.muqhc.gamepaper.format

import io.github.muqhc.skollobleparser.Element

interface FormatMultiElement<TargetType>: Format<TargetType, List<Element>>