val moneyVersion = "1.0.4"
val monetaVersion = "1.4.1"

dependencies {
    compile(project(":valiktor-core"))
    compileOnly("javax.money:money-api-bp:$moneyVersion")

    testCompile(kotlin("reflect"))
    testCompile("javax.money:money-api-bp:$moneyVersion")
    testRuntime("org.javamoney:moneta-bp:$monetaVersion")
}
