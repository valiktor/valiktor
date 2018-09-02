import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun DependencyHandler.springBoot(module: String) = "org.springframework.boot:spring-boot-$module:2.0.4.RELEASE"

plugins {
    kotlin("plugin.spring") version "1.2.51"
}

dependencies {
    compile(project(":valiktor-spring:valiktor-spring"))
    compile(project(":valiktor-spring:valiktor-spring-boot-autoconfigure"))
    compile(springBoot("starter"))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}