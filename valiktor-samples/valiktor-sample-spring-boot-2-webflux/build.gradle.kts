plugins {
    kotlin("plugin.spring") version "1.4.10"
    id("org.springframework.boot") version "2.3.4.RELEASE"
}

val jacksonMoneyVersion = "1.2.0"
val moneyVersion = "1.1"
val monetaVersion = "1.4.2"

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.3.4.RELEASE"))

    compile(kotlin("stdlib"))
    compile(kotlin("reflect"))
    compile(project(":valiktor-javamoney"))
    compile(project(":valiktor-javatime"))
    compile(project(":valiktor-spring:valiktor-spring-boot-starter"))
    compile("org.springframework.boot:spring-boot-starter-webflux")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    compile("org.zalando:jackson-datatype-money:$jacksonMoneyVersion")
    compile("javax.money:money-api:$moneyVersion")
    runtime("org.javamoney:moneta:$monetaVersion")

    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testCompile("org.jetbrains.kotlinx:kotlinx-coroutines-debug")
}
