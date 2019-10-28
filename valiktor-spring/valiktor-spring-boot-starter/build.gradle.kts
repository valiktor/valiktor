plugins {
    kotlin("plugin.spring") version "1.3.50"
}

val springBootVersion = "2.1.5.RELEASE"

dependencies {
    compile(project(":valiktor-spring:valiktor-spring"))
    compile(project(":valiktor-spring:valiktor-spring-boot-autoconfigure"))
    compile("org.springframework.boot:spring-boot-starter:$springBootVersion")
}
