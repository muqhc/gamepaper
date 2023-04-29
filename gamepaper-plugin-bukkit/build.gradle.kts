dependencies {
    implementation("io.github.monun:kommand-api:3.1.3")
    testImplementation("io.papermc.paper:paper-api:${rootProject.ext["paper-api.version"]}-R0.1-SNAPSHOT")
}

tasks {
    processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
    }
}