fun DependencyHandler.springBoot(module: String) = "org.springframework.boot:spring-boot-$module:1.5.15.RELEASE"

plugins {
    kotlin("plugin.spring") version "1.2.51"
}

dependencies {
    compile(project(":valiktor-spring:valiktor-spring"))
    compile(project(":valiktor-spring:valiktor-spring-boot-autoconfigure"))
    compile(springBoot("starter"))
    compile(springBoot("starter-web"))
}