dependencies {
    compile(kotlin("stdlib"))
    compile(kotlin("reflect"))
    compile(project(":valiktor-core"))
    compile(project(":valiktor-jodatime"))
    compile("joda-time:joda-time:2.10.2")
}