package io.github.muqhc.gamepaper.sample

import io.github.muqhc.gamepaper.exception.InvalidConfigException
import io.github.muqhc.gamepaper.format.FormatSingleElement
import io.github.muqhc.skollobleparser.Element
import org.bukkit.Color
import kotlin.reflect.full.staticProperties

object ColorSequenceFormat: FormatSingleElement<List<Color>> {
    override val defaultGenText: String = """
        {
            color: red ;
            color: green ;
            color: blue ;
            color: rgb"ABCDEF" ;
        }
    """.trimIndent()

    override fun checkIsValid(target: Element) {
        target.children.takeIf {
            it.all {
                it.name == "color" &&
                        it.children.isEmpty() &&
                        it.attribution.keys.count() == 1 &&
                        (
                                it.attribution.keys.toList()[0] == "rgb" ||
                                        it.attribution.entries.toList()[0].let { (k, v) -> k == v.value }
                                )
            }
        } ?: throw InvalidConfigException("""
            Invalid Color Sequence Config!
            Valid Example :
            ${target.name} {
                color: red \
                color: green \
                color: blue \
                color: rgb"ABCDEF" \
            }
        """.trimIndent())
    }

    override fun construct(target: Element): List<Color> =
        target.children
            .map {
                val key = it.attribution.keys.toList()[0]
                if (key != "rgb")
                    Color::class.staticProperties.find {
                        it.name.lowercase() == key.lowercase()
                    }!!.get() as Color
                else
                    Color.fromRGB(Integer.parseInt(it.attribution[key]!!.value,16))
            }

}