val jodaTimeVersion = "2.10.2"

dependencies {
    compile(project(":valiktor-core"))
    compileOnly("joda-time:joda-time:$jodaTimeVersion")

    testCompile(kotlin("reflect"))
    testCompile("joda-time:joda-time:$jodaTimeVersion")
}
