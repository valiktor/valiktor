allprojects {
    tasks {
        dokka {
            enabled = false
        }

        publishMavenJavaPublicationToMavenLocal {
            enabled = false
        }

        publishMavenJavaPublicationToMavenRepository {
            enabled = false
        }
    }
}

subprojects {
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
}
