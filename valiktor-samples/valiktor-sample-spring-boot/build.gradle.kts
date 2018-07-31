import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("plugin.spring") version "1.2.51"
    id("org.springframework.boot") version "1.5.4.RELEASE"
}

apply {
    plugin("io.spring.dependency-management")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":valiktor-core"))
    implementation(project(":valiktor-javamoney"))
    implementation(project(":valiktor-javatime"))
    implementation(project(":valiktor-spring:valiktor-spring-boot-starter"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("org.zalando:jackson-datatype-money:1.0.2")
    implementation("javax.money:money-api:1.0.3")
    runtimeOnly("org.javamoney:moneta:1.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}