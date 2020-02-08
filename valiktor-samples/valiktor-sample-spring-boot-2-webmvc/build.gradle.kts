plugins {
    kotlin("plugin.spring") version "1.3.61"
    id("org.springframework.boot") version "2.2.4.RELEASE"
}

apply {
    plugin("io.spring.dependency-management")
}

val jacksonMoneyVersion = "1.1.1"
val moneyVersion = "1.0.3"
val monetaVersion = "1.3"

dependencies {
    compile(kotlin("stdlib"))
    compile(kotlin("reflect"))
    compile(project(":valiktor-javamoney"))
    compile(project(":valiktor-javatime"))
    compile(project(":valiktor-spring:valiktor-spring-boot-starter"))
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    compile("org.zalando:jackson-datatype-money:$jacksonMoneyVersion")
    compile("javax.money:money-api:$moneyVersion")
    runtime("org.javamoney:moneta:$monetaVersion")

    testCompile("org.springframework.boot:spring-boot-starter-test")
}
