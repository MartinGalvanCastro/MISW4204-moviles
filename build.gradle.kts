// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.githooks) apply false
    alias(libs.plugins.dotenv) apply false
    alias(libs.plugins.openapi.generator) apply false
    alias(libs.plugins.hilt) apply false
    id("jacoco")
    id("com.google.gms.google-services") version "4.4.2" apply false
}


subprojects {
    afterEvaluate {
        project.apply("../gradleScripts/spotless.gradle")
    }
}

buildscript {
    dependencies {
        classpath(libs.org.jacoco.core) // Jacoco library
    }
}