package io.github.muqhc.gamepaper.format

interface Format<TargetType,ResourceType> {
    fun checkIsValid(target: ResourceType): Boolean
    fun construct(target: ResourceType): TargetType
}
