fun springBoot(module: String) = "org.springframework.boot:spring-boot-$module:2.1.5.RELEASE"
fun jacksonModule(module: String) = "com.fasterxml.jackson.module:jackson-module-$module:2.9.9"

plugins {
    kotlin("plugin.spring") version "1.3.31"
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