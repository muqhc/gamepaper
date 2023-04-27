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

        implementation("org.apache.maven.resolver:maven-resolver-impl:1.9.8")
        implementation("org.apache.maven.resolver:maven-resolver-connector-basic:1.9.8")
        implementation("org.apache.maven.resolver:maven-resolver-transport-file:1.9.8")
        implementation("org.apache.maven.resolver:maven-resolver-transport-http:1.9.8")

        implementation("org.apache.maven:maven-resolver-provider:3.9.1")

        if (name.contains("bukkit")) {
            compileOnly("io.papermc.paper:paper-api:${rootProject.ext["paper-api.version"]}-R0.1-SNAPSHOT")
            if (!name.contains("api") && !name.contains("core"))
                implementation(project(":${rootProject.name}-core-bukkit"))
        }
        if (name.contains("core") || name.contains("plugin")) {
            api(project(":"+(name.replace("core","api").replace("plugin","api"))))
            if (name != "${rootProject.name}-core")
                api(project(":${rootProject.name}-core"))
        }
        if (name.contains("api")) {
            if (name != "${rootProject.name}-api")
                api(project(":${rootProject.name}-api"))
        }
    }
}
