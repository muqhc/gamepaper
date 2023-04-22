dependencies {
    implementation(project(":${rootProject.name}-bukkit"))
}

tasks {
    processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
    }
}