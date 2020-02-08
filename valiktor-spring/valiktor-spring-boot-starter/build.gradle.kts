plugins {
    kotlin("plugin.spring") version "1.3.61"
}

val springBootVersion = "2.2.4.RELEASE"

dependencies {
    compile(project(":valiktor-spring:valiktor-spring"))
    compile(project(":valiktor-spring:valiktor-spring-boot-autoconfigure"))
    compile("org.springframework.boot:spring-boot-starter:$springBootVersion")
}
