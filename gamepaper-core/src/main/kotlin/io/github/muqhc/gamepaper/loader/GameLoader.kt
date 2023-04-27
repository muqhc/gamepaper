package io.github.muqhc.gamepaper.loader

import io.github.muqhc.gamepaper.Game
import io.github.muqhc.gamepaper.GamePack
import io.github.muqhc.gamepaper.dependency.DependencyLoadingSystem
import io.github.muqhc.gamepaper.game.ConfigureWith
import io.github.muqhc.gamepaper.game.GameInfo
import java.io.File
import java.util.concurrent.ConcurrentHashMap

class GameLoader(val dependencyLoadingSystem: DependencyLoadingSystem) {
    private val classes: MutableMap<String, Class<*>?> = ConcurrentHashMap()

    private val classLoaders: MutableMap<File, GameClassLoader> = ConcurrentHashMap()

    fun load(jarFile: File): GamePack {
        require(jarFile !in classLoaders) { "Already registered file ${jarFile.name}" }

        val classLoader = GameClassLoader(this, jarFile, javaClass.classLoader)

        val info = GameInfo.of(jarFile)

        try {
            val gameClass =
                Class.forName(info.mainClass, true, classLoader).asSubclass(Game::class.java)
            val configClass =
                gameClass.annotations.filterIsInstance<ConfigureWith>().single().target.java

            classLoaders[jarFile] = classLoader

            val result = GamePack(jarFile, configClass, gameClass, info)

            val configFile = File(jarFile.parentFile,"${result.info.id}.config.skolloble")
            if (!configFile.exists()) result.createConfigFile(jarFile.parentFile)
            result.createGameConfigProxy(configFile)

            result.configProxy.apply {
                dependencyLoadingSystem.load(
                    classLoader,
                    repositories ?: listOf(),
                    dependencies ?: listOf()
                )
            }

            return result
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