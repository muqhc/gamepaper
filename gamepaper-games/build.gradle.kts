subprojects {
    apply(plugin="org.jetbrains.kotlin.jvm")
    dependencies {
        implementation(project(":${rootProject.name}-bukkit"))
        implementation(project(":${rootProject.name}-core"))
        implementation("io.github.muqhc:skolloble-parser:1.5.0")
    }
}

tasks.build {
    dependsOn(*subprojects.map { ":${project.name}:${it.name}:build" }.toTypedArray())
    File("${rootProject.rootDir.path}${File.separator}games").delete()
    copy {
        from(subprojects.map { File(it.buildDir,"libs").path })
        into("${rootProject.rootDir.path}${File.separator}games")
    }
}