plugins {
    this.kotlin("jvm") version "1.8.10"
}


val versionsPropsFile = rootDir.resolve("dependencies.versions.properties")
if (versionsPropsFile.exists()) {
    val versionProps = `java.util`.Properties()
    versionProps.load(versionsPropsFile.inputStream())
    versionProps.forEach { (k, v) -> if (k is String) ext.set(k, v) }
}

allprojects {
    repositories {
        mavenCentral()
        maven(url=uri("https://papermc.io/repo/repository/maven-public/"))
    }
}

subprojects {
    apply(plugin="org.jetbrains.kotlin.jvm")

    dependencies {
        implementation(kotlin("stdlib"))
        implementation(kotlin("reflect"))
        implementation("io.github.muqhc:skolloble-parser:${rootProject.ext["skolloble-parser.version"]}")
        if (name.contains("bukkit")) {
            compileOnly("io.papermc.paper:paper-api:${rootProject.ext["paper-api.version"]}-R0.1-SNAPSHOT")
        }
        if (!name.endsWith("core")) {
            implementation(project(":${rootProject.name}-core"))
        }
    }
}
