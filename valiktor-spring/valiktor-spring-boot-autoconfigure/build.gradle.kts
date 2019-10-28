plugins {
    kotlin("plugin.spring") version "1.3.50"
}

val springBootVersion = "2.1.5.RELEASE"
val jacksonVersion = "2.9.9"

dependencies {
    compileOnly(project(":valiktor-spring:valiktor-spring"))
    compileOnly("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    compile("org.springframework.boot:spring-boot-autoconfigure:$springBootVersion")
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor:$springBootVersion")

    testCompile(project(":valiktor-spring:valiktor-spring"))
    testCompile("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    testCompile("org.springframework.boot:spring-boot-starter-webflux:$springBootVersion")
    testCompile("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testCompile("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
}
