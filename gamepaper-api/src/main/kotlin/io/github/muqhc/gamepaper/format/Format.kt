package io.github.muqhc.gamepaper.format

interface Format<TargetType,ResourceType> {
    val defaultGenText: String
    fun checkIsValid(target: ResourceType)
    fun construct(target: ResourceType): TargetType
}
