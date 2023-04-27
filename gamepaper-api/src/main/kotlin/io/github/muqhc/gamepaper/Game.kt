package io.github.muqhc.gamepaper

import io.github.muqhc.gamepaper.config.GameConfig
import io.github.muqhc.gamepaper.game.GameLifeCycleInterface

abstract class Game<ConfigTemplate:GameConfig>: GameLifeCycleInterface {
    var ___configProxy: GameConfig? = null //TODO: 접근성 제한하기
    val config: ConfigTemplate
        get() = ___configProxy!! as ConfigTemplate
}