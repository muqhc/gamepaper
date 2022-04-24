package io.github.muqhc.gamepaper.dependency

import java.net.URL

data class Dependency(
    val repoUrlText: String,
    val repoUrl: URL,
    val wholeUrlText: String,
    val wholeUrl: URL,
    val notation: String,
)