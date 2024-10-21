plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.spotless)
    alias(libs.plugins.openapi.generator)  // OpenAPI Generator plugin
}

android {
    namespace = "com.example.vinilosapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.vinilosapp"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    sourceSets {
        getByName("main").java.srcDir("build/generated/src/main/kotlin")  // Specify where the generated code will go
    }
}

openApiGenerate {
    generatorName.set("kotlin")                                 // Specify generator type
    inputSpec.set("$rootDir/open-api.json")             // Path to OpenAPI JSON file
    outputDir.set(layout.buildDirectory.dir("generated").get().asFile.path)                     // Directory for generated code
    apiPackage.set("com.example.api")                           // API package
    modelPackage.set("com.example.models")                      // Models package
    invokerPackage.set("com.example.invoker")                   // Invoker package
    library.set("jvm-retrofit2")                                    // Use Retrofit2 as the library
}

tasks.named("preBuild") {
    dependsOn("openApiGenerate")
}


dependencies {
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
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.android.mavericks)
    implementation(libs.mavericks.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.kotlin.faker)
    implementation(libs.coil.network.okhttp)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    implementation(libs.retrofit2.converter.scalars)

    testImplementation(libs.junit)
    testImplementation(libs.coil.test)
    testImplementation(libs.mvrx.testing)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
