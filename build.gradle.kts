@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.safeArgs) apply false
}
buildscript {
    dependencies {
        classpath(libs.safe.args)
    }
}
true