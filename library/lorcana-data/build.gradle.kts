import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.nio.file.Files

@Suppress("DSL_SCOPE_VIOLATION")
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

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

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
                api(libs.multiplatform.moko.resources)
                api(libs.multiplatform.moko.resources.ext)
                api(additionals.kotlinx.coroutines)
                api(additionals.kotlinx.serialization.json)
                api(additionals.multiplatform.file.access)
                api(additionals.multiplatform.http.client)

                api("net.mamoe.yamlkt:yamlkt:0.13.0")
                api(libs.tcg.mapper)
            }
        }
        val commonTest by getting {
            dependencies {
                api(kotlin("test"))
                api(additionals.kotlinx.coroutines.test)
                api(additionals.multiplatform.platform)
            }
        }

        val androidMain by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val jvmMain by getting
        val jsMain by getting {
            dependencies {
                implementation(libs.moko.parcelize)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.moko.parcelize)
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
