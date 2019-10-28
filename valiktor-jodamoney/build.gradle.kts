val jodaMoneyVersion = "1.0.1"

dependencies {
    compile(project(":valiktor-core"))
    compileOnly("org.joda:joda-money:$jodaMoneyVersion")

    testCompile(kotlin("reflect"))
    testCompile("org.joda:joda-money:$jodaMoneyVersion")
}
