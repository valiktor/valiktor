import org.jetbrains.dokka.gradle.DokkaTask

tasks {
    withType<DokkaTask> {
        enabled = false
    }

    withType<AbstractPublishToMaven> {
        enabled = false
    }
}