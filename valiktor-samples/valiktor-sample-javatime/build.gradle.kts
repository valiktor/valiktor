dependencies {
    compile(kotlin("stdlib"))
    compile(kotlin("reflect"))
    compile(project(":valiktor-core"))
    compile(project(":valiktor-javatime"))

    testCompile(project(":valiktor-test"))
}
