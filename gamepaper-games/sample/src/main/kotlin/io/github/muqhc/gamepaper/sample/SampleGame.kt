package io.github.muqhc.gamepaper.sample

import io.github.muqhc.gamepaper.Game
import io.github.muqhc.gamepaper.game.ConfigureWith

@ConfigureWith(SampleGameConfig::class)
class SampleGame(val times: Int): Game<SampleGameConfig>() {
    constructor(): this(1)
    override fun onEnable() {
        println("Hello Gamepaper!")
        println(config.colorSequence)
        println(config.colors)
        repeat(times) {
            println("Hello! x$it")
        }

    }
}