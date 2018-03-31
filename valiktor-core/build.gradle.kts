import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("jvm") version "1.2.30"
}

repositories {
    mavenCentral()
}

dependencies {
    kotlin("kotlin-stdlib-jdk8")
    kotlin("kotlin-reflect")
}