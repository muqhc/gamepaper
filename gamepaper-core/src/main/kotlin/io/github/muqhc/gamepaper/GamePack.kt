package io.github.muqhc.gamepaper

import io.github.muqhc.gamepaper.config.GameConfig
import io.github.muqhc.gamepaper.config.generateConfig
import io.github.muqhc.gamepaper.proxy.implementGameConfig
import io.github.muqhc.gamepaper.util.skollobleOfFile
import io.github.muqhc.gamepaper.util.skollobleOfJar
import io.github.muqhc.gamepaper.util.valueMapOfOnResource
import io.github.muqhc.gamepaper.util.valueMapOfOutOfResource
import java.io.File
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaGetter

class GamePack(val jarFile: File, val configClass: Class<out GameConfig>, val gameClass: Class<out Game<*>>) {
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
        val id = GameConfig.skollobleOfJar(jarFile).children.find { it.name == "id" }!!.strings[0]
        val file = File("$path${File.separator}$id.skolloble")
        file.writeText(generateConfig(configClass.kotlin))
        file.createNewFile()
    }

    fun newGameInstance(args: List<Any>) =
        gameClass.getConstructor(*args.map { it.javaClass }.toTypedArray()).newInstance()
}