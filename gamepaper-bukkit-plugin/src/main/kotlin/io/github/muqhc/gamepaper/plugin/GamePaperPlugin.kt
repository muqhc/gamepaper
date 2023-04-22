package io.github.muqhc.gamepaper.plugin

import io.github.monun.kommand.kommand
import io.github.muqhc.gamepaper.GamePack
import io.github.muqhc.gamepaper.dependency.DependencyLoadingSystem
import io.github.muqhc.gamepaper.loader.GameLoader
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class GamePaperPlugin: JavaPlugin() {
    lateinit var gamesDirectory: File
    lateinit var gamePacks: List<GamePack>

    val gameLoader = GameLoader(DependencyLoadingSystem())

    override fun onEnable() {
        basicLoadProcess()
    }

    fun prepareFiles() {
        gamesDirectory = File(gamesDirectory,"games").let {
            require(it.isDirectory)
            if (!it.exists()) it.mkdir()
            it
        }
    }

    fun prepareGamePacks() {
        val gameJars = gamesDirectory.listFiles().filter { it.extension == "jar" }
        gamePacks = gameJars.map {
            gameLoader.load(it)
        }
    }

    fun reload() {
        basicLoadProcess()
    }

    fun basicLoadProcess() {
        prepareFiles()
        prepareGamePacks()
    }

    fun kommandConfiguration() {
        kommand {
            register("gamepaper") {
                then("create_game") {

                }
            }
        }
    }
}