package io.github.muqhc.gamepaper

import io.github.muqhc.gamepaper.config.GameConfig
import io.github.muqhc.gamepaper.game.GameLifeCycleInterface

abstract class Game<ConfigTemplate:GameConfig>: GameLifeCycleInterface {
    internal var configProxy: GameConfig? = null
    val config: ConfigTemplate
        get() = configProxy!! as ConfigTemplate
}