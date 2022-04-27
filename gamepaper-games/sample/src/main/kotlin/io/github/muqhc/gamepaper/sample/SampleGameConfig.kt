package io.github.muqhc.gamepaper.sample

import io.github.muqhc.gamepaper.config.*
import org.bukkit.Color

interface SampleGameConfig: GameConfig {

    @GameConfigEntry("color sequence")
    @ResourceConfig
    @SpecificFormatSingle<List<Color>>(ColorSequenceFormat::class)
    val colorSequence: List<Color>

    @GameConfigEntry("color list")
    @DefaultGeneratingTextValue("""
        {
            color: red \
            color: green \
            color: blue \
            color: rgb"ABCDEF" \
        }
    """)
    val colors: List<Color>

}