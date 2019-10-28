val jodaMoneyVersion = "1.0.1"

dependencies {
    compile(kotlin("stdlib"))
    compile(kotlin("reflect"))
    compile(project(":valiktor-core"))
    compile(project(":valiktor-jodamoney"))
    compile("org.joda:joda-money:$jodaMoneyVersion")

    testCompile(project(":valiktor-test"))
}
