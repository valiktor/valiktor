dependencies {
    compile(kotlin("stdlib"))
    compile(kotlin("reflect"))
    compile(project(":valiktor-core"))
    compile(project(":valiktor-javamoney"))
    compile("javax.money:money-api:1.0.3")

    testRuntime("org.javamoney:moneta:1.3")
}