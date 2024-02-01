import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Files

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(dolbyio.plugins.android.library)
    alias(dolbyio.plugins.kotlin.multiplatform)
    alias(dolbyio.plugins.kotlin.serialization)
    alias(dolbyio.plugins.multiplatform.moko.resources.generator)
    alias(dolbyio.plugins.multiplatform.buildkonfig)
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
                implementation(dolbyio.multiplatform.moko.resources.ext)
                api(dolbyio.kotlinx.coroutines)
                api(dolbyio.kotlinx.serialization.json)
                api(dolbyio.multiplatform.file.access)
                api(dolbyio.multiplatform.http.client)

                api("net.mamoe.yamlkt:yamlkt:0.13.0")
            }
        }
        val commonTest by getting {
            dependencies {
                api(kotlin("test"))
                api(dolbyio.kotlinx.coroutines.test)
                api(dolbyio.multiplatform.platform)
            }
        }

        val androidMain by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val jvmMain by getting
        val jsMain by getting

        listOf(
            androidMain,
            iosX64Main,
            iosArm64Main,
            iosSimulatorArm64Main,
            jvmMain,
            jsMain
        ).forEach { it.dependsOn(commonMain) }
    }
}

android {
    namespace = "eu.codlab.lorcana"
}

multiplatformResources {
    multiplatformResourcesPackage = "eu.codlab.lorcana.resources"
    multiplatformResourcesClassName = "Resources"
    multiplatformResourcesVisibility = dev.icerock.gradle.MRVisibility.Public
}

buildkonfig {
    packageName = "eu.codlab.lorcana.buildconfig"

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "commit", rootProject.extra["commit"] as String)
    }
}

val original = file("${rootProject.projectDir.absolutePath}/data")
val link = file("src/commonMain/resources/MR/files")

if (!link.exists()) {
    link.parentFile.mkdirs()
    Files.createSymbolicLink(link.toPath(), original.toPath())
}

tasks.register("generateMR") {
    group = "moko-resources"
    tasks.matching { it.name.startsWith("generateMR") && it.name.endsWith("Main") }
        .forEach { this.dependsOn(it) }

    tasks.withType<KotlinCompile>().forEach { it.dependsOn(this) }
    tasks.matching { it.name.endsWith("SourcesJar") }.forEach {
        it.mustRunAfter(this)
    }
}

// also configure the sub tasks
afterEvaluate {
    val toRunAfter = tasks.matching { task ->
        null != listOf("SourcesJar", "ProcessResources").find { task.name.endsWith(it) }
    }

    tasks.matching { it.name.startsWith("generateMR") && it.name.endsWith("Main") }
        .forEach { mokoResource ->
            toRunAfter.forEach {
                it.mustRunAfter(mokoResource)
            }
        }
}
