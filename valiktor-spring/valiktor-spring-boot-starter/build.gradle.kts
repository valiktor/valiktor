fun DependencyHandler.springBoot(module: String) = "org.springframework.boot:spring-boot-$module:2.1.4.RELEASE"

plugins {
    kotlin("plugin.spring") version "1.3.21"
}

dependencies {
    compile(project(":valiktor-spring:valiktor-spring"))
    compile(project(":valiktor-spring:valiktor-spring-boot-autoconfigure"))
    compile(springBoot("starter"))
}