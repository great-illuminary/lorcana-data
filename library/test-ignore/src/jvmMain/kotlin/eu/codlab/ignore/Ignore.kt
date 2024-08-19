package eu.codlab.ignore

import org.junit.jupiter.api.Disabled

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
actual annotation class IgnoreAndroid

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
actual annotation class IgnoreJs

actual typealias IgnoreJvm = Disabled
