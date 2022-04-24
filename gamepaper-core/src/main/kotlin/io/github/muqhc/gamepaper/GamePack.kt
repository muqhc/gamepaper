package io.github.muqhc.gamepaper

import io.github.muqhc.gamepaper.config.GameConfig
import io.github.muqhc.gamepaper.proxy.implementGameConfig
import io.github.muqhc.gamepaper.util.skollobleOfFile
import io.github.muqhc.gamepaper.util.skollobleOfJar
import io.github.muqhc.gamepaper.util.valueMapOfOnResource
import io.github.muqhc.gamepaper.util.valueMapOfOutOfResource
import java.io.File
import kotlin.reflect.KProperty1

class GamePack(val jarFile: File, val configClass: Class<out GameConfig>, val gameClass: Class<out Game<*>>) {
    lateinit var configPropValueMap: Map<KProperty1<out GameConfig,*>,Any?>
    lateinit var configProxy: GameConfig

    fun createGameConfigProxy(configFile: File) {
        val skbleOfFile = GameConfig.skollobleOfFile(configFile)
        val skbleOfJar = GameConfig.skollobleOfJar(jarFile)

        configPropValueMap = GameConfig.valueMapOfOnResource(configClass.kotlin,skbleOfJar)
            .plus(GameConfig.valueMapOfOutOfResource(configClass.kotlin,skbleOfFile))

        configProxy = implementGameConfig(configClass,configPropValueMap)
    }

    fun newGameInstance() = gameClass.getConstructor().newInstance()
}