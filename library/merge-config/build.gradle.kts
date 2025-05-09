import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(additionals.plugins.kotlin.multiplatform)
    alias(additionals.plugins.android.library)
    alias(additionals.plugins.multiplatform.buildkonfig)
    id("jvmCompat")
    id("iosSimulatorConfiguration")
}

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget {
        publishLibraryVariants("release", "debug")
    }

    jvm()

    js(IR) {
        browser()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }

    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.get().compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }
}

android {
    namespace = "eu.codlab.lorcana"
}

buildkonfig {
    packageName = "eu.codlab.lorcana"

    defaultConfigs {
        listOf(
            "lorcana" to "LORCANA",
        ).forEach {
            buildConfigField(
                FieldSpec.Type.STRING,
                it.first,
                rootProject.extra[it.second] as String
            )
        }
    }
}
