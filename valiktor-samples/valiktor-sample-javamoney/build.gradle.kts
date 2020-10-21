val moneyVersion = "1.1"
val monetaVersion = "1.4.2"

dependencies {
    compile(kotlin("stdlib"))
    compile(kotlin("reflect"))
    compile(project(":valiktor-core"))
    compile(project(":valiktor-javamoney"))
    compile("javax.money:money-api:$moneyVersion")

    testCompile(project(":valiktor-test"))
    testRuntime("org.javamoney:moneta:$monetaVersion")
}
