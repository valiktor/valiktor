import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("jvm") version "1.2.31"
}

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile(kotlin("reflect"))
}