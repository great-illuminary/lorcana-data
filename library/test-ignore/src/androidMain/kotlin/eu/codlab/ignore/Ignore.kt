package eu.codlab.ignore

import org.junit.jupiter.api.Disabled

actual typealias IgnoreAndroid = Disabled

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
actual annotation class IgnoreJs

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
actual annotation class IgnoreJvm
