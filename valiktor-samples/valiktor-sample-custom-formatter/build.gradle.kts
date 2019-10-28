dependencies {
    compile(kotlin("stdlib"))
    compile(kotlin("reflect"))
    compile(project(":valiktor-core"))

    testCompile(project(":valiktor-test"))
}
