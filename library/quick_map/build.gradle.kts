@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    application
    alias(additionals.plugins.kotlin.jvm)
    id("jvmCompat")
}

group = "com.github.codlab"
version = "1.0"

application {
    mainClass.set("MainKt")
}

dependencies {
    api(project(":lorcana-data"))
    api(additionals.multiplatform.file.access)
}
