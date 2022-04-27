package io.github.muqhc.gamepaper.loader

import io.github.muqhc.gamepaper.Game
import io.github.muqhc.gamepaper.GamePack
import io.github.muqhc.gamepaper.config.GameConfig
import io.github.muqhc.gamepaper.game.GameInfo
import java.io.File
import java.util.concurrent.ConcurrentHashMap

object GameLoader {
    private val classes: MutableMap<String, Class<*>?> = ConcurrentHashMap()

    private val classLoaders: MutableMap<File, GameClassLoader> = ConcurrentHashMap()

    fun load(jarFile: File): GamePack {
        require(jarFile !in classLoaders) { "Already registered file ${jarFile.name}" }

        val classLoader = GameClassLoader(this, jarFile, javaClass.classLoader)

        val info = GameInfo.of(jarFile)

        try {
            val gameClass =
                Class.forName(info.mainClass, true, classLoader).asSubclass(Game::class.java)
            val configClassName =
                gameClass.kotlin.supertypes.first().arguments.first().type.toString().removePrefix("class ")
            val configClass = Class.forName(configClassName, true, classLoader).asSubclass(GameConfig::class.java)

            classLoaders[jarFile] = classLoader

            return GamePack(jarFile, configClass, gameClass, info)
        } catch (e: Exception) {
            classLoader.close()
            throw e
        }
    }

    fun findClass(name: String, skip: GameClassLoader): Class<*> {
        var found = classes[name]

        if (found != null) return found

        for (loader in classLoaders.values) {
            if (loader === skip) continue

            try {
                found = loader.findLocalClass(name)
                classes[name] = found

                return found
            } catch (ignore: ClassNotFoundException) {
            }
        }

        throw ClassNotFoundException(name)
    }
}