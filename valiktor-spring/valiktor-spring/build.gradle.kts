fun spring(module: String) = "org.springframework:spring-$module:5.1.7.RELEASE"
fun servlet(module: String) = "javax.servlet:javax.servlet-$module:4.0.1"
fun jacksonCore(module: String) = "com.fasterxml.jackson.core:jackson-$module:2.9.9"
fun jacksonModule(module: String) = "com.fasterxml.jackson.module:jackson-module-$module:2.9.9"
fun jacksonDataFormat(module: String) = "com.fasterxml.jackson.dataformat:jackson-dataformat-$module:2.9.9"
fun jsonAssert() = "org.skyscreamer:jsonassert:1.5.0"
fun xmlUnit() = "org.xmlunit:xmlunit-core:2.6.2"

plugins {
    kotlin("plugin.spring") version "1.3.31"
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