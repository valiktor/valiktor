import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun DependencyHandler.junit(module: String) = "org.junit.jupiter:junit-jupiter-$module:5.1.0"
fun DependencyHandler.assertj(module: String) = "org.assertj:assertj-$module:3.9.1"

plugins {
    kotlin("jvm") version "1.2.31"
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("jacoco")
    }

    group = "org.valiktor"
    version = "0.1.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))

        testImplementation(assertj("core"))
        testImplementation(junit("api"))
        testRuntimeOnly(junit("engine"))
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }

        withType<Test> {
            useJUnitPlatform()
        }

        withType<JacocoReport> {
            reports {
                xml.isEnabled = true
                html.isEnabled = true
            }

            val jacocoTestCoverageVerification by tasks
            jacocoTestCoverageVerification.dependsOn(this)
        }

        withType<JacocoCoverageVerification> {
            violationRules {
                rule {
                    limit {
                        minimum = BigDecimal.valueOf(1.0)
                    }
                }
            }
            val check by tasks
            check.dependsOn(this)
        }
    }
}