dependencies {
    implementation(project(":${rootProject.name}-core"))
}

tasks {
    processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
    }
}