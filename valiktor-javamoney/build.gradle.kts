fun DependencyHandler.javaMoney(module: String) = "javax.money:money-$module:1.0.3"
fun DependencyHandler.moneta(module: String) = "org.javamoney:$module:1.3"

dependencies {
    implementation(project(":valiktor-core"))
    implementation(javaMoney("api"))

    testImplementation(kotlin("reflect"))

    testRuntimeOnly(moneta("moneta"))
}