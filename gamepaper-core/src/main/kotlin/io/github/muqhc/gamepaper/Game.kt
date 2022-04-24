package io.github.muqhc.gamepaper

import io.github.muqhc.gamepaper.config.GameConfig
import io.github.muqhc.gamepaper.game.GameLifeCycleInterface

abstract class Game<ConfigTemplate:GameConfig>: GameLifeCycleInterface {
    var configProxy: ConfigTemplate? = null
        internal set
    val config: ConfigTemplate
        get() = configProxy!!
}