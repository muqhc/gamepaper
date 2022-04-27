package io.github.muqhc.gamepaper.game

import io.github.muqhc.gamepaper.config.GameConfig
import io.github.muqhc.gamepaper.util.skollobleOfJar
import java.io.File

data class GameInfo(
    val id: String,
    val mainClass: String
) {
    companion object {
        @JvmStatic fun of(jarFile: File) = GameConfig.skollobleOfJar(jarFile).run {
            GameInfo(
                children.find { it.name == "id" }!!.strings[0],
                children.find { it.name == "mainClass" }!!.strings[0]
            )
        }
    }
}
