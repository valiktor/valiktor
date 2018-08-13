import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    compile(project(":valiktor-core"))

    testCompile(kotlin("reflect"))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}