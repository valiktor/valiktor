import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun DependencyHandler.spring(module: String) = "org.springframework:spring-$module:5.0.8.RELEASE"
fun DependencyHandler.servlet(module: String) = "javax.servlet:javax.servlet-$module:3.0.1"
fun DependencyHandler.jacksonCore(module: String) = "com.fasterxml.jackson.core:jackson-$module:2.9.6"
fun DependencyHandler.jacksonModule(module: String) = "com.fasterxml.jackson.module:jackson-module-$module:2.9.6"
fun DependencyHandler.jacksonDataFormat(module: String) = "com.fasterxml.jackson.dataformat:jackson-dataformat-$module:2.9.6"
fun DependencyHandler.jsonAssert() = "org.skyscreamer:jsonassert:1.5.0"
fun DependencyHandler.xmlUnit() = "org.xmlunit:xmlunit-core:2.6.2"

plugins {
    kotlin("plugin.spring") version "1.2.51"
}

dependencies {
    compile(project(":valiktor-core"))
    compileOnly(spring("webmvc"))
    compileOnly(spring("webflux"))
    compileOnly(jacksonModule("kotlin"))
    compileOnly(jacksonDataFormat("xml"))

    testCompile(spring("webmvc"))
    testCompile(spring("webflux"))
    testCompile(spring("test"))
    testCompile(servlet("api"))
    testCompile(jacksonCore("databind"))
    testCompile(jacksonModule("kotlin"))
    testCompile(jacksonDataFormat("xml"))
    testRuntime(jsonAssert())
    testRuntime(xmlUnit())
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}