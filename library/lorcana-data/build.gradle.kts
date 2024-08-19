import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.nio.file.Files

plugins {
    alias(additionals.plugins.android.library)
    alias(additionals.plugins.kotlin.multiplatform)
    alias(additionals.plugins.kotlin.serialization)
    alias(libs.plugins.multiplatform.moko.resources.generator)
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
                api(libs.multiplatform.moko.resources)
                api(libs.multiplatform.moko.resources.ext)

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

multiplatformResources {
    resourcesPackage = "eu.codlab.lorcana.resources"
    resourcesClassName = "Resources"
    resourcesVisibility = dev.icerock.gradle.MRVisibility.Public
}

buildkonfig {
    packageName = "eu.codlab.lorcana.buildconfig"

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "commit", rootProject.extra["commit"] as String)
    }
}

val original = file("${rootProject.projectDir.absolutePath}/data")
val link = file("src/commonMain/moko-resources/files")

if (!link.exists()) {
    link.parentFile.mkdirs()
    Files.createSymbolicLink(link.toPath(), original.toPath())
}
