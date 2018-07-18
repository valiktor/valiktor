dependencies {
    implementation(project(":valiktor-core"))
    implementation("javax.money:money-api:1.0.3")

    testRuntimeOnly("org.javamoney:moneta:1.3")
}