plugins {
    kotlin("jvm") version "1.4.10"
    id("jacoco")
    id("signing")
    id("maven-publish")
    id("org.jetbrains.dokka") version "0.10.1"
    id("org.jmailen.kotlinter") version "3.2.0"
    id("com.adarshr.test-logger") version "2.0.0"
}

repositories {
    mavenCentral()
    jcenter()
}

subprojects {
    val junitVersion = "5.5.2"
    val assertjVersion = "3.15.0"

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
        testCompile("org.assertj:assertj-core:$assertjVersion")
        testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    }

    testlogger {
        setTheme("mocha")
    }

    kotlinter {
        disabledRules = arrayOf("import-ordering")
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

        processResources {
            filteringCharset = "UTF-8"
            filesMatching("**/*.properties") {
                filter(org.apache.tools.ant.filters.EscapeUnicode::class)
            }
        }

        test {
            useJUnitPlatform()

            // fix for JDK > 8 (see http://openjdk.java.net/jeps/252)
            systemProperty("java.locale.providers", "JRE,SPI")
        }

        processTestResources {
            filteringCharset = "UTF-8"
            filesMatching("**/*.properties") {
                filter(org.apache.tools.ant.filters.EscapeUnicode::class)
            }
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
                    description.set("Valiktor is a type-safe, powerful and extensible fluent DSL to validate objects in Kotlin.")
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
