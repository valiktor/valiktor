fun joda(module: String) = "org.joda:joda-$module:1.0.1"

dependencies {
    compile(project(":valiktor-core"))
    compileOnly(joda("money"))

    testCompile(kotlin("reflect"))
    testCompile(joda("money"))
}