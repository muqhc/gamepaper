package io.github.muqhc.gamepaper.format

fun interface Format<TargetType,ResourceType> {
    fun construct(target: ResourceType): TargetType
}
