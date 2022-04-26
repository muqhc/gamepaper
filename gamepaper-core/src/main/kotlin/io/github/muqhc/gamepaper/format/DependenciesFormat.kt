package io.github.muqhc.gamepaper.format

import io.github.muqhc.gamepaper.dependency.Dependency
import io.github.muqhc.skollobleparser.Attribution
import io.github.muqhc.skollobleparser.Element
import java.net.URL

object DependenciesFormat: FormatMultiElement<List<Dependency>> {
    override fun checkIsValid(target: List<Element>) {
//        TODO("Not yet implemented")
    }

    override fun construct(target: List<Element>): List<Dependency> =
        target.flatMap { dependents ->
            dependents.strings.map { notation ->
                val repoUrlText = dependencyUrlPick(dependents.attribution)
                val dependUrlText = repoUrlText + notation.split(":").let {
                    "${it[0].replace(".", "/")}/${it[1]}/${it[2]}"
                }
                Dependency(
                    repoUrlText,
                    URL(repoUrlText),
                    dependUrlText,
                    URL(dependUrlText),
                    notation
                )
            }
        }


    fun dependencyUrlPick(attribution: Attribution): String {
        attribution.keys.forEach { attrId ->
            when (attrId) {
                "mavenCentral" -> return "https://repo.maven.apache.org/maven2/"
                "url" -> return attribution["url"]!!.value.let {
                    if (!it.endsWith("/")) "$it/" else it
                }
            }
        }
        return "https://repo.maven.apache.org/maven2/"
    }
}