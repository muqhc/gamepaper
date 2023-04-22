package io.github.muqhc.gamepaper.format

import io.github.muqhc.gamepaper.dependency.RepositoryInfo
import io.github.muqhc.skollobleparser.Element

object RepositoryInfoListFormat: FormatSingleElement<List<RepositoryInfo>> {
    override val defaultGenText: String = "{\n    mavenCentral;\n}"

    override fun checkIsValid(target: Element) {
//        TODO("Not yet implemented")
    }

    override fun construct(target: Element): List<RepositoryInfo> {
        return target.children.map {
            if (it.name == "mavenCentral")
                return@map RepositoryInfo("mavenCentral","https://repo.maven.apache.org/maven2/")

            RepositoryInfo(it.name,it.strings.single())
        }
    }
}