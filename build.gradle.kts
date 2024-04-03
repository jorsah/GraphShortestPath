// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.android.build.gradle.LibraryExtension

buildscript {
    dependencies {
        // just the matter of fact there is no chance right know to move it in plugins section
        // thx google :(
        classpath(libs.android.tools.build)
    }
}

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.dagger.hilt.plugin) apply false
    alias(libs.plugins.kotlin.kapt) apply false
}
true // Needed to make the Suppress annotation work for the plugins block