fun DependencyHandler.javaMoney(module: String) = "javax.money:money-$module:1.0.3"
fun DependencyHandler.moneta(module: String) = "org.javamoney:$module:1.3"

dependencies {
    compile(project(":valiktor-core"))
    compileOnly(javaMoney("api-bp"))

    testCompile(kotlin("reflect"))
    testCompile(javaMoney("api-bp"))
    testRuntime(moneta("moneta-bp"))
}