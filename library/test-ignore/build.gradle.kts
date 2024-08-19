plugins {
    alias(additionals.plugins.android.library)
    alias(additionals.plugins.kotlin.multiplatform)
    id("jvmCompat")
    id("publication")
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")
    }

    jvm()

    js(IR) {
        browser()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(additionals.junit.jupiter.api)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(additionals.junit.jupiter.api)
            }
        }
    }
}

android {
    namespace = "eu.codlab.ignore"
}
