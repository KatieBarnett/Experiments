import org.jetbrains.kotlin.utils.addToStdlib.safeAs

// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.dagger.hilt.android) apply (false)
    alias(libs.plugins.ksp) apply (false)
    alias(libs.plugins.protobuf) apply (false)
//    alias(libs.plugins.safeArgs) apply (false)
}

ext {
    extra["appVersionName"] = "1.0.0"
    extra["appVersionCode"] = 1
    extra["compileSdk"] = 34
    extra["targetSdk"] = 34
    extra["minSdk"] = 26
    extra["wearMinSdk"] = 30
}

true // Needed to make the Suppress annotation work for the plugins block