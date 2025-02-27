@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    application
    alias(additionals.plugins.kotlin.jvm)
    alias(additionals.plugins.kotlin.serialization)
    id("jvmCompat")
}

group = "com.github.codlab"
version = "1.0"

application {
    mainClass.set("MergeKt")
}

dependencies {
    api(project(":lorcana-data"))
    api(additionals.multiplatform.file.access)
}
