import org.jetbrains.dokka.gradle.DokkaTask

allprojects {
    tasks {
        withType<DokkaTask> {
            enabled = false
        }

        withType<AbstractPublishToMaven> {
            enabled = false
        }
    }
}