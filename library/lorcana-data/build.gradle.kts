import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(additionals.plugins.android.library)
    alias(additionals.plugins.kotlin.multiplatform)
    alias(additionals.plugins.kotlin.serialization)
    alias(additionals.plugins.jetbrains.compose)
    alias(additionals.plugins.compose.compiler)
    alias(additionals.plugins.multiplatform.buildkonfig)
    id("iosSimulatorConfiguration")
    id("jvmCompat")
    id("publication")
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")
    }

    jvm()

    js(IR) {
        browser {
            testTask {
                useMocha {
                    timeout = "30s"
                }
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                api(compose.components.resources)

                api(additionals.kotlinx.coroutines)
                api(additionals.kotlinx.serialization.json)

                api(additionals.multiplatform.file.access)
                api(additionals.multiplatform.http.client)

                api(libs.yaml.serializer)
                api(libs.tcg.mapper)
            }
        }
        val commonTest by getting {
            dependencies {
                api(kotlin("test"))
                implementation(project(":test-ignore"))
                api(additionals.kotlinx.coroutines.test)
                api(additionals.multiplatform.platform)
            }
        }
    }
}

android {
    namespace = "eu.codlab.lorcana"
}

compose.resources {
    publicResClass = true
    packageOfResClass = "eu.codlab.lorcana.resources"
    generateResClass = always
}

buildkonfig {
    packageName = "eu.codlab.lorcana.buildconfig"

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "commit", rootProject.extra["commit"] as String)
    }
}

// TODO -> check for file differences to only copy the newest
val validExtensions = listOf("yml", "yaml")
val original = file("${rootProject.projectDir.absolutePath}/data")
val link = file("src/commonMain/composeResources/files").also { it.mkdirs() }
val files = original.listFiles()?.filter { validExtensions.contains(it.extension) }

files?.forEach {
    it.copyTo(File(link, "${it.name}.txt"), overwrite = true)
}
