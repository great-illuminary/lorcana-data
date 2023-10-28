@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(dolbyio.plugins.kotlin.jvm)
    alias(dolbyio.plugins.kotlin.serialization)
    id("jvmCompat")
}

dependencies {
    api(dolbyio.kotlinx.coroutines.jvm)
    api(dolbyio.kotlinx.serialization.json)
    api(dolbyio.multiplatform.file.access)

    testApi(kotlin("test"))
}
