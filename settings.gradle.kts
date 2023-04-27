rootProject.name = "gamepaper"

val moduleNameList = listOf(
    "api",
    "core",
    "api-bukkit",
    "core-bukkit",
    "games",
    "plugin-bukkit"
).map { "${rootProject.name}-$it" }

rootProject.projectDir.list().forEach {
    if (moduleNameList.contains(it)) include(it)
}

project(":${moduleNameList.find { it.contains("games") }}").projectDir.listFiles().forEach {
    if (it.isDirectory && it.name != "build") include(":${moduleNameList.find { it.contains("games") }}:${it.name}")
}