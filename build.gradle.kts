import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.bundling.Jar
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("jvm") version "1.3.11"
    id("jacoco")
    id("signing")
    id("maven-publish")
    id("org.jetbrains.dokka") version "0.9.17"
    id("org.jmailen.kotlinter") version "1.20.1"
    id("com.adarshr.test-logger") version "1.6.0"
}

repositories {
    mavenCentral()
    jcenter()
}

subprojects {
    fun DependencyHandler.assertj(module: String) = "org.assertj:assertj-$module:3.11.1"
    fun DependencyHandler.junit5(module: String) = "org.junit.jupiter:junit-jupiter-$module:5.0.0"

    apply {
        plugin("kotlin")
        plugin("jacoco")
        plugin("signing")
        plugin("maven-publish")
        plugin("org.jetbrains.dokka")
        plugin("org.jmailen.kotlinter")
        plugin("com.adarshr.test-logger")
    }

    group = "org.valiktor"

    repositories {
        mavenCentral()
        jcenter()
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
        compileKotlin {
            kotlinOptions {
                jvmTarget = "1.6"
            }
        }

        compileTestKotlin {
            kotlinOptions {
                jvmTarget = "1.6"
            }
        }

        test {
            useJUnitPlatform()

            // fix for JDK > 8 (see http://openjdk.java.net/jeps/252)
            systemProperty("java.locale.providers", "JRE,SPI")
        }

        dokka {
            outputFormat = "javadoc"
            outputDirectory = "$buildDir/javadoc"
        }

        jacocoTestReport {
            reports {
                xml.isEnabled = true
                html.isEnabled = true
            }
        }

        jacocoTestCoverageVerification {
            dependsOn(jacocoTestReport)

            violationRules {
                rule { limit { minimum = 0.3.toBigDecimal() } }
            }
        }

        check {
            dependsOn(jacocoTestCoverageVerification)
        }
    }

    publishing {
        val ossrhUsername: String by project
        val ossrhPassword: String by project

        repositories {
            maven(url = "https://oss.sonatype.org/service/local/staging/deploy/maven2/") {
                credentials {
                    username = ossrhUsername
                    password = ossrhPassword
                }
            }
        }
        publications {
            create<MavenPublication>("mavenJava") {
                val binaryJar = components["java"]

                val sourcesJar by tasks.creating(Jar::class) {
                    archiveClassifier.set("sources")
                    from(sourceSets["main"].allSource)
                }

                val javadocJar by tasks.creating(Jar::class) {
                    archiveClassifier.set("javadoc")
                    from("$buildDir/javadoc")
                }

                from(binaryJar)
                artifact(sourcesJar)
                artifact(javadocJar)

                pom {
                    name.set("Valiktor")
                    description.set("Valiktor is a type-safe, powerful and extensible fluent DSL to validate objects written in Kotlin.")
                    url.set("https://www.valiktor.org")
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("rodolphocouto")
                            name.set("Rodolpho Sbaraglini Couto")
                            email.set("rodolpho.sbaraglini@gmail.com")
                        }
                    }
                    scm {
                        url.set("https://www.github.com/valiktor/valiktor")
                        connection.set("scm:git:https://www.github.com/valiktor/valiktor")
                        developerConnection.set("scm:git:https://www.github.com/rodolphocouto")
                    }
                }
            }
        }
    }

    afterEvaluate {
        signing {
            sign(publishing.publications["mavenJava"])
        }
    }
}