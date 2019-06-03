fun jodaTime(module: String) = "joda-time:$module:2.10.2"

dependencies {
    compile(project(":valiktor-core"))
    compileOnly(jodaTime("joda-time"))

    testCompile(kotlin("reflect"))
    testCompile(jodaTime("joda-time"))
}