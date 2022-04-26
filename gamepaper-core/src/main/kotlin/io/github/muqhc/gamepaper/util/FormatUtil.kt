package io.github.muqhc.gamepaper.util

import io.github.muqhc.gamepaper.exception.InvalidConfigException
import io.github.muqhc.gamepaper.format.FormatMultiElement
import io.github.muqhc.gamepaper.format.FormatSingleElement
import io.github.muqhc.skollobleparser.Element

fun <TargetType> FormatSingleElement<TargetType>.constructOnValid(target: Element): TargetType {
    runCatching { checkIsValid(target) }
        .onFailure {
            throw InvalidConfigException("Config '${target.name}' is invalid",it)
        }
    return construct(target)
}

fun <TargetType> FormatMultiElement<TargetType>.constructOnValid(target: List<Element>): TargetType {
    runCatching { checkIsValid(target) }
        .onFailure {
            throw InvalidConfigException("Config '${target[0].name}' is invalid",it)
        }
    return construct(target)
}