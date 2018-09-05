import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun DependencyHandler.springBoot(module: String) = "org.springframework.boot:spring-boot-$module:2.0.4.RELEASE"
fun DependencyHandler.jacksonModule(module: String) = "com.fasterxml.jackson.module:jackson-module-$module:2.9.6"

plugins {
    kotlin("plugin.spring") version "1.2.51"
}

dependencies {
    compileOnly(project(":valiktor-spring:valiktor-spring"))
    compileOnly(springBoot("starter-web"))
    compile(springBoot("autoconfigure"))
    annotationProcessor(springBoot("autoconfigure-processor"))

    testCompile(project(":valiktor-spring:valiktor-spring"))
    testCompile(springBoot("starter-web"))
    testCompile(springBoot("starter-webflux"))
    testCompile(springBoot("test"))
    testCompile(jacksonModule("kotlin"))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}