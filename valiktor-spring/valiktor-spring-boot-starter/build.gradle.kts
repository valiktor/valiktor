import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun DependencyHandler.springBoot(module: String) = "org.springframework.boot:spring-boot-$module:1.5.15.RELEASE"

plugins {
    kotlin("plugin.spring") version "1.2.51"
}

dependencies {
    compile(project(":valiktor-spring:valiktor-spring"))
    compile(project(":valiktor-spring:valiktor-spring-boot-autoconfigure"))
    compile(kotlin("stdlib-jdk8"))
    compile(springBoot("starter"))
    compile(springBoot("starter-web"))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}