subprojects {
    apply(plugin="org.jetbrains.kotlin.jvm")
    dependencies {
        implementation(project(":${rootProject.name}-bukkit"))
        implementation(project(":${rootProject.name}-core"))
        implementation("io.github.muqhc:skolloble-parser:${rootProject.ext["skolloble-parser.version"]}")
        compileOnly("io.papermc.paper:paper-api:${rootProject.ext["paper-api.version"]}-R0.1-SNAPSHOT")
        testImplementation("io.papermc.paper:paper-api:${rootProject.ext["paper-api.version"]}-R0.1-SNAPSHOT")
    }
}

tasks.build {
    dependsOn(*subprojects.map { ":${project.name}:${it.name}:build" }.toTypedArray())
    doLast {
        copy {
            from(subprojects.map { File(it.buildDir, "libs").path })
            into("${rootProject.rootDir.path}${File.separator}games")
        }
    }
}