package io.github.muqhc.gamepaper.config

import io.github.muqhc.gamepaper.dependency.RepositoryInfo
import io.github.muqhc.gamepaper.format.RepositoryInfoListFormat

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
    val dependencies: List<String>?

    @GameConfigEntry("Game's maven repositories for dependencies")
    @ResourceConfig
    @SpecificFormatSingle(RepositoryInfoListFormat::class)
    val repositories: List<RepositoryInfo>?

}