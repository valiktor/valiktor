val jodaTimeVersion = "2.10.2"

dependencies {
    compile(kotlin("stdlib"))
    compile(kotlin("reflect"))
    compile(project(":valiktor-core"))
    compile(project(":valiktor-jodatime"))
    compile("joda-time:joda-time:$jodaTimeVersion")

    testCompile(project(":valiktor-test"))
}
