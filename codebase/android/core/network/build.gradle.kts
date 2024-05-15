plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.makeappssimple.abhimanyu.financemanager.android.core.network"
    compileSdk = 34 // rootProject.compileSdkVersion

    defaultConfig {
        minSdk = 27 // rootProject.minSdkVersion

        testInstrumentationRunner =
            "com.makeappssimple.abhimanyu.financemanager.android.core.testing.MyTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"

        freeCompilerArgs += listOf(
            // Explicit API mode
            "-Xexplicit-api=strict",
        )
    }
}

dependencies {
    implementation(project(":core:common"))

    // Coroutines
    implementation(libs.coroutines.android)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // KSP
    implementation(libs.ksp)

    // KotlinX serialization
    implementation(libs.serialization)

    // KotlinX collections immutable
    implementation(libs.collections.immutable)

    // Okhttp logging interceptor
    implementation(libs.okhttp)

    // Retrofit kotlinX serialization converter
    implementation(libs.retrofit.serialization.converter)

    // Detekt compose
    detektPlugins(libs.detekt.rules.compose.nlopez)

    // Testing

    // JUnit
    testImplementation(libs.junit)

    // Android X test
    androidTestImplementation(libs.test.core)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.truth)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.test.rules)

    // Mockito kotlin
    testImplementation(libs.mockito.kotlin)

    // Coroutines test
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.coroutines.test)
}
