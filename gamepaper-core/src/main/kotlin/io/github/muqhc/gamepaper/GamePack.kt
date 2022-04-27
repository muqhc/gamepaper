package io.github.muqhc.gamepaper

import io.github.muqhc.gamepaper.config.GameConfig
import io.github.muqhc.gamepaper.util.generateConfig
import io.github.muqhc.gamepaper.game.GameInfo
import io.github.muqhc.gamepaper.proxy.implementGameConfig
import io.github.muqhc.gamepaper.util.skollobleOfFile
import io.github.muqhc.gamepaper.util.skollobleOfJar
import io.github.muqhc.gamepaper.util.valueMapOfOnResource
import io.github.muqhc.gamepaper.util.valueMapOfOutOfResource
import java.io.File
import kotlin.reflect.jvm.javaGetter

class GamePack(val jarFile: File, val configClass: Class<out GameConfig>, val gameClass: Class<out Game<*>>, val info: GameInfo) {
    lateinit var configPropValueMap: Map<String,Any?>
    lateinit var configProxy: GameConfig

    fun createGameConfigProxy(configFile: File) {
        val skbleOfFile = GameConfig.skollobleOfFile(configFile)
        val skbleOfJar = GameConfig.skollobleOfJar(jarFile)

        configPropValueMap = GameConfig.valueMapOfOnResource(configClass.kotlin,skbleOfJar)
            .plus(GameConfig.valueMapOfOutOfResource(configClass.kotlin,skbleOfFile))
            .mapKeys { it.key.javaGetter!!.name }

        configProxy = implementGameConfig(configClass,configPropValueMap)
    }

    fun createConfigFile(path: String) {
        val file = File("$path${File.separator}${info.id}.skolloble")
        file.writeText(generateConfig(configClass.kotlin,info))
        file.createNewFile()
    }

    fun newGameInstance(args: List<Any>) =
        gameClass.getConstructor(*args.map { it.javaClass }.toTypedArray()).newInstance()
}