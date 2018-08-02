import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("plugin.spring") version "1.2.51"
    id("org.springframework.boot") version "1.5.15.RELEASE"
}

apply {
    plugin("io.spring.dependency-management")
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile(kotlin("reflect"))
    compile(project(":valiktor-javamoney"))
    compile(project(":valiktor-javatime"))
    compile(project(":valiktor-spring:valiktor-spring-boot-starter"))
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.6")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.9.6")
    compile("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.9.6")
    compile("org.zalando:jackson-datatype-money:1.0.2")
    compile("javax.money:money-api:1.0.3")
    runtime("org.javamoney:moneta:1.3")

    testCompile("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}