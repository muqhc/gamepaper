package io.github.muqhc.gamepaper.format

interface Format<TargetType,ResourceType> {
    fun checkIsValid(target: ResourceType)
    fun construct(target: ResourceType): TargetType
    fun constructOnValid(target: ResourceType): TargetType {
        checkIsValid(target)
        return construct(target)
    }
}
