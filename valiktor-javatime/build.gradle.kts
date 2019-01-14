import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    compile(project(":valiktor-core"))

    testCompile(kotlin("reflect"))
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}