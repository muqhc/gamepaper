dependencies {
    implementation("io.github.monun:kommand-api:3.1.3")
}

tasks {
    processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
    }
}