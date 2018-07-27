import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun DependencyHandler.spring(module: String) = "org.springframework:spring-$module:4.3.18.RELEASE"

plugins {
    kotlin("plugin.spring") version "1.2.51"
}

dependencies {
    implementation(project(":valiktor-core"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(spring("webmvc"))

    testImplementation(kotlin("reflect"))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}