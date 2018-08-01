import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun DependencyHandler.spring(module: String) = "org.springframework:spring-$module:4.3.18.RELEASE"
fun DependencyHandler.servlet(module: String) = "javax.servlet:javax.servlet-$module:3.0.1"
fun DependencyHandler.jacksonCore(module: String) = "com.fasterxml.jackson.core:jackson-$module:2.9.6"
fun DependencyHandler.jacksonModule(module: String) = "com.fasterxml.jackson.module:jackson-module-$module:2.9.6"
fun DependencyHandler.jacksonDataFormat(module: String) = "com.fasterxml.jackson.dataformat:jackson-dataformat-$module:2.9.6"
fun DependencyHandler.jsonAssert() = "org.skyscreamer:jsonassert:1.5.0"
fun DependencyHandler.xmlUnit() = "xmlunit:xmlunit:1.6"

plugins {
    kotlin("plugin.spring") version "1.2.51"
}

dependencies {
    implementation(project(":valiktor-core"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(spring("webmvc"))
    compileOnly(jacksonModule("kotlin"))
    compileOnly(jacksonDataFormat("xml"))

    testImplementation(spring("test"))
    testImplementation(servlet("api"))
    testImplementation(jacksonCore("databind"))
    testImplementation(jacksonModule("kotlin"))
    testImplementation(jacksonModule("jaxb-annotations"))
    testImplementation(jacksonDataFormat("xml"))
    testRuntimeOnly(jsonAssert())
    testRuntimeOnly(xmlUnit())
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}