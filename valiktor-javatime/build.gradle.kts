import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    compile(project(":valiktor-core"))
    compile(kotlin("stdlib-jdk8"))

    testCompile(kotlin("reflect"))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}