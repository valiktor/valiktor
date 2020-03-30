plugins {
    kotlin("plugin.spring") version "1.3.71"
}

val springVersion = "5.2.5.RELEASE"
val jacksonVersion = "2.10.3"
val servletVersion = "4.0.1"
val jsonAssertVersion = "1.5.0"
val xmlUnitVersion = "2.6.4"

dependencies {
    compile(project(":valiktor-core"))
    compileOnly("org.springframework:spring-webmvc:$springVersion")
    compileOnly("org.springframework:spring-webflux:$springVersion")
    compileOnly("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")

    testCompile("org.springframework:spring-webmvc:$springVersion")
    testCompile("org.springframework:spring-webflux:$springVersion")
    testCompile("org.springframework:spring-test:$springVersion")
    testCompile("javax.servlet:javax.servlet-api:$servletVersion")
    testCompile("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    testCompile("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    testCompile("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")
    testRuntime("org.skyscreamer:jsonassert:$jsonAssertVersion")
    testRuntime("org.xmlunit:xmlunit-core:$xmlUnitVersion")
}
