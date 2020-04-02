val coroutinesVersion = "1.3.5"

dependencies {
    compile(kotlin("reflect"))
    compile(kotlin("test"))
    compile(project(":valiktor-core"))

    testCompile("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    testCompile("org.jetbrains.kotlinx:kotlinx-coroutines-debug:$coroutinesVersion")
}
