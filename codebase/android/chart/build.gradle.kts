plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.kover)
}

android {
    namespace = "com.makeappssimple.abhimanyu.financemanager.android.chart"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()

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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12" // jetpackComposeCompilerVersion
    }
}

dependencies {
    implementation(project(":core:designsystem"))

    lintChecks(project(":lint"))

    // Androidx core
    implementation(libs.androidx.core)

    // Jetpack compose
    implementation(libs.bundles.compose)

    // Testing

    // JUnit
    testImplementation(libs.junit)

    // Android X test
    androidTestImplementation(libs.bundles.test)

    // Mockito kotlin
    testImplementation(libs.mockito.kotlin)

    // Coroutines test
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.coroutines.test)
}
