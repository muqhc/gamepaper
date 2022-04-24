package io.github.muqhc.gamepaper.config

import io.github.muqhc.gamepaper.dependency.Dependency
import io.github.muqhc.gamepaper.format.DependenciesFormat

interface GameConfig {
    companion object

    @GameConfigEntry("Game's identifier")
    @ResourceConfig
    val id: String

    @GameConfigEntry("Game's name that will be displayed")
    @ResourceConfig
    val name: String

    @GameConfigEntry("Main Game Class Path")
    @ResourceConfig
    val mainClass: String

    @GameConfigEntry("Game's dependencies library on maven repository")
    @ResourceConfig
    @SpecificFormatMulti<List<Dependency>>(DependenciesFormat::class)
    val dependencies: List<Dependency>?

}