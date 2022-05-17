plugins {
    kotlin("jvm") version "1.6.10"
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
        implementation("io.github.muqhc:skolloble-parser:1.5.1")
        if (!name.endsWith("core")) {
            implementation("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
            implementation(project(":${rootProject.name}-core")
        }
    }
}
