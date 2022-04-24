package io.github.muqhc.gamepaper.sample

import io.github.muqhc.gamepaper.config.GameConfigEntry
import io.github.muqhc.gamepaper.config.GameConfig
import io.github.muqhc.gamepaper.config.ResourceConfig
import io.github.muqhc.gamepaper.config.SpecificFormatSingle
import org.bukkit.Color

interface SampleGameConfig: GameConfig {

    @GameConfigEntry("color sequence")
    @ResourceConfig
    @SpecificFormatSingle<List<Color>>(ColorSequenceFormat::class)
    val colorSequence: List<Color>

}