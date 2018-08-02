import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.2.51"
    id("com.adarshr.test-logger") version "1.3.1"
}

subprojects {
    fun DependencyHandler.assertj(module: String) = "org.assertj:assertj-$module:3.9.1"
    fun DependencyHandler.junit5(module: String) = "org.junit.jupiter:junit-jupiter-$module:5.0.0"

    apply {
        plugin("kotlin")
        plugin("jacoco")
        plugin("com.adarshr.test-logger")
    }

    group = "org.valiktor"
    version = "0.1.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        compile(kotlin("stdlib"))

        testCompile(kotlin("test-junit5"))
        testCompile(assertj("core"))
        testRuntime(junit5("engine"))
    }

    testlogger {
        setTheme("mocha")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.6"
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
                        minimum = BigDecimal.valueOf(0.4)
                    }
                }
            }
            val check by tasks
            check.dependsOn(this)
        }
    }
}