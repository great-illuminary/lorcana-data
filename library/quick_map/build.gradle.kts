@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    application
    alias(dolbyio.plugins.kotlin.jvm)
    alias(dolbyio.plugins.jetbrains.compose)
    id("jvmCompat")
}

group = "com.github.codlab"
version = "1.0"

application {
    mainClass.set("MainKt")
}

dependencies {
    api(project(":lorcana-data"))
    api(compose.desktop.currentOs)
    api(dolbyio.multiplatform.file.access)
}
