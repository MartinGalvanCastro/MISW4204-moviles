plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.spotless)
    alias(libs.plugins.openapi.generator)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
    id("jacoco")
}

android {
    namespace = "com.example.vinilosapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.vinilosapp"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"


        testInstrumentationRunner = "com.example.vinilosapp.config.VinilosAndroidJUnitRunner"
        testInstrumentationRunnerArguments["optionsAnnotationPackage"] = "com.example.vinilosapp.config"
        testInstrumentationRunnerArguments["glue"] = "com.example.vinilosapp.steps"

    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(11))
        }
    }


    buildTypes {
        debug {
            // Hardcoded localhost URL for emulator
            buildConfigField("String", "BASE_URL", "\"http://localhost:3000\"")
        }
        release {
            // Hardcoded production URL
            buildConfigField("String", "BASE_URL", "\"https://production-backend.com\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    sourceSets {
        getByName("main") {
            java.srcDir(layout.buildDirectory.dir("generated/src/main/kotlin").get())
        }
    }

    lint {
        baseline = file("lint-baseline.xml")
    }

}

jacoco {
    toolVersion = "0.8.8"
}

openApiGenerate {
    generatorName.set("kotlin")
    inputSpec.set("$rootDir/open-api.json")
    outputDir.set(layout.buildDirectory.dir("generated").get().asFile.path)
    apiPackage.set("com.example.api")
    modelPackage.set("com.example.models")
    invokerPackage.set("com.example.invoker")
    library.set("jvm-retrofit2")


    configOptions.set(
        mapOf(
            "dateLibrary" to "string"
        )
    )
}


tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacocoHtml"))
    }

    classDirectories.setFrom(
        fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/debug")) {
            exclude(
                // Exclude common generated Android classes
                "**/R.class",
                "**/R$*.class",
                "**/BuildConfig.*",
                "**/Manifest*.*",
                "**/*Test*.*",                    // Exclude test classes

                // Exclude specific packages
                "com/example/api/**",             // Exclude API package
                "com/example/models/**",          // Exclude Models package
                "org/openapitools/client/infrastructure/**", // Exclude OpenAPI infrastructure
                "hilt_aggregated_deps/**",        // Exclude Hilt aggregated dependencies

                // Exclude DataBinding and Hilt-generated files
                "android/databinding/**",         // Exclude DataBinding generated classes
                "**/databinding/**",
                "**/*_Factory.class",             // Exclude Dagger/Hilt factories
                "**/*_HiltModules*.*",            // Exclude Dagger/Hilt modules
                "**/*_GeneratedInjector.class",   // Exclude Hilt Injector generated classes
                "**/MainActivity_GeneratedInjector*.*", // Exclude specific generated Injector

                // Exclude all files inside the build directory
                "**/build/**"
            )
        }
    )

    sourceDirectories.setFrom(files("src/main/java", "src/main/kotlin"))
    executionData.setFrom(fileTree(layout.buildDirectory) {
        include("jacoco/testDebugUnitTest.exec")
    })
}





tasks.named("preBuild") {
    dependsOn("openApiGenerate")
}

tasks.withType<Test> {
    finalizedBy(tasks.named("jacocoTestReport"))
}



dependencies {
    implementation(libs.androidx.ui.test.android)
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.r8)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)

    implementation(libs.coil.compose)
    implementation(libs.coil)
    implementation(libs.coil.svg)
    implementation(libs.coil.cache)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.android.mavericks)
    implementation(libs.mavericks.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.kotlin.faker)
    implementation(libs.coil.network.okhttp)
    implementation(libs.okhttp)
    implementation(libs.okhttp.tls)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    implementation(libs.retrofit2.converter.scalars)
    implementation(libs.hilt.android)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit)
    testImplementation(libs.junit.junit)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.jakewharton.threetenabp)


    testImplementation(libs.junit)
    testImplementation(libs.coil.test)
    testImplementation(libs.mvrx.testing)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.okhttp3.mockwebserver)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.hamcrest.library)
    testImplementation(libs.mavericks.mocking)


    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.mvrx.testing)
    androidTestImplementation(libs.mavericks.mocking)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.cucumber.android)
    androidTestImplementation(libs.cucumber.hilt)
    androidTestImplementation(libs.cucumber.junit.rules.support)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.androidx.uiautomator)
    androidTestImplementation(libs.androidx.core)


    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}


kapt {
    correctErrorTypes = true
}
