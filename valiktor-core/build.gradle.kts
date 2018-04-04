import org.gradle.kotlin.dsl.kotlin

fun junit(module: String) = "org.junit.jupiter:junit-jupiter-$module:5.1.0"

plugins {
    kotlin("jvm")
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile(kotlin("reflect"))

    testImplementation(junit("api"))
    testRuntimeOnly(junit("engine"))
}