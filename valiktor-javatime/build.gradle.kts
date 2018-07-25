import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation(project(":valiktor-core"))
    implementation(kotlin("stdlib-jdk8"))

    testImplementation(kotlin("reflect"))
}

tasks {
    withType<KotlinCompile> {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()

        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}