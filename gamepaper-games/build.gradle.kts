subprojects {
    apply(plugin="org.jetbrains.kotlin.jvm")
    dependencies {
        implementation(project(":${rootProject.name}-core"))
    }
    tasks {
        processResources {
            filesMatching("**/*.skolloble") {
                expand(project.properties)
            }
            filesMatching("**/*.skble") {
                expand(project.properties)
            }
        }
    }
}