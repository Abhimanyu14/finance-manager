plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.kover)
}

android {
    namespace = "com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog"
    compileSdk = 34 // rootProject.compileSdkVersion

    defaultConfig {
        applicationId = "com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog"
        minSdk = 27 // rootProject.minSdkVersion
        targetSdk = 34 // rootProject.targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "com.makeappssimple.abhimanyu.financemanager.android.core.testing.MyTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false

            isTestCoverageEnabled = false
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

            // Kotlin
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlin.contracts.ExperimentalContracts",
            "-opt-in=kotlin.ExperimentalUnsignedTypes",
            "-opt-in=kotlin.time.ExperimentalTime",

            // Kotlinx
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.FlowPreview",
            "-opt-in=kotlinx.coroutines.InternalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.ObsoleteCoroutinesApi",

            // Compose
            "-opt-in=androidx.compose.runtime.ExperimentalComposeApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
            "-opt-in=androidx.compose.animation.core.InternalAnimationApi",
        )
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12" // jetpackComposeCompilerVersion
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":chart"))
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))

    testImplementation(project(":core:testing"))

    androidTestImplementation(project(":core:testing"))

    lintChecks(project(":lint"))

    // Androidx core
    implementation(libs.androidx.core)

    // Lifecycle components
    implementation(libs.lifecycle.runtime.ktx)

    // Jetpack compose
    implementation(libs.compose.ui.androidx)
    implementation(libs.compose.util)

    // Jetpack compose tooling support (Previews, etc.)
    debugImplementation(libs.compose.tooling)
    implementation(libs.compose.preiew)

    // Jetpack compose material design
    implementation(libs.compose.material3)

    // Jetpack compose material design adapter
    implementation(libs.material.compose.theme.adapter)

    // Jetpack compose integration with activities
    implementation(libs.activity.compose)

    // Jetpack compose integration with view models
    implementation(libs.lifecycle.viewmodel.compose)

    // Jetpack compose lifecycle runtime
    implementation(libs.lifecycle.runtime.compose)

    // Jetpack compose navigation
    implementation(libs.navigation.compose)

    // Testing

    // Jetpack compose UI tests
    androidTestImplementation(libs.compose.ui.test.junit4)

    // Jetpack compose UI tests manifest - Needed for createComposeRule, but not createAndroidComposeRule
    debugImplementation(libs.compose.ui.test.manifest)
}