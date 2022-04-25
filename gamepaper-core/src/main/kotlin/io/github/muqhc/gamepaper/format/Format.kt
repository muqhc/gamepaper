package io.github.muqhc.gamepaper.format

interface Format<TargetType,ResourceType> {
    fun checkIsValid(targetType: ResourceType): Boolean
    fun construct(target: ResourceType): TargetType
}
