plugins {
    kotlin("plugin.spring") version "1.4.10"
    id("org.springframework.boot") version "1.5.22.RELEASE"
}

val jacksonVersion = "2.9.10"
val jacksonMoneyVersion = "1.2.0"
val moneyVersion = "1.1"
val monetaVersion = "1.4.2"
val springTestJunit5 = "1.5.0"

repositories {
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:1.5.22.RELEASE"))

    compile(kotlin("stdlib"))
    compile(kotlin("reflect"))
    compile(project(":valiktor-javamoney"))
    compile(project(":valiktor-javatime"))
    compile(project(":valiktor-spring:valiktor-spring-boot-starter"))
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    compile("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")
    compile("org.zalando:jackson-datatype-money:$jacksonMoneyVersion")
    compile("javax.money:money-api:$moneyVersion")
    runtime("org.javamoney:moneta:$monetaVersion")

    testCompile("org.springframework.boot:spring-boot-starter-test")
    // for Spring Boot 1.x + JUnit 5 support
    testCompile("com.github.sbrannen:spring-test-junit5:$springTestJunit5")
}
