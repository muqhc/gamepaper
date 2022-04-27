package io.github.muqhc.gamepaper.sample

import io.github.muqhc.gamepaper.Game

class SampleGame: Game<SampleGameConfig>() {
    override fun onEnable() {
        println("Hello Gamepaper!")
    }
}