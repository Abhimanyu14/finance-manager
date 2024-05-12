plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlinx.kover)
}

android {
    namespace = "com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor"
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
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:logger"))
    implementation(project(":core:navigation"))
    implementation(project(":core:model"))
    implementation(project(":core:ui"))

    testImplementation(project(":core:testing"))

    androidTestImplementation(project(":core:testing"))

    lintChecks(project(":lint"))

    // Androidx core
    implementation(libs.androidx.core)

    // Lifecycle components
    implementation(libs.lifecycle.runtime.ktx)

    // Appcompat
    implementation(libs.appcompat)

    // Coroutines
    implementation(libs.coroutines.android)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Jetpack compose
    implementation(libs.compose.ui.androidx)
    implementation(libs.compose.util)

    // Jetpack compose tooling support (Previews, etc.)
    debugImplementation(libs.compose.tooling)
    implementation(libs.compose.preiew)

    // Jetpack compose material design
    implementation(libs.compose.material3)

    // Jetpack compose integration with view models
    implementation(libs.lifecycle.viewmodel.compose)

    // Jetpack compose lifecycle runtime
    implementation(libs.lifecycle.runtime.compose)

    // Jetpack compose navigation
    implementation(libs.navigation.compose)

    // Jetpack compose navigation with hilt
    implementation(libs.hilt.navigation.compose)

    // Testing

    // JUnit
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit)

    // Android X test
    testImplementation(libs.test.core)
    androidTestImplementation(libs.test.core)
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.junit)
    testImplementation(libs.test.truth)
    androidTestImplementation(libs.test.truth)
    testImplementation(libs.test.runner)
    androidTestImplementation(libs.test.runner)
    testImplementation(libs.test.rules)
    androidTestImplementation(libs.test.rules)

    // Mockito kotlin
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.mockito.kotlin)

    // Coroutines test
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.coroutines.test)

    // AndroidX core test
    testImplementation(libs.core.testing)
    androidTestImplementation(libs.core.testing)

    // Turbine
    testImplementation(libs.turbine)
    androidTestImplementation(libs.turbine)

    // Hilt testing
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.android.compiler)
    kspAndroidTest(libs.hilt.android.compiler)

    // Jetpack compose UI tests
    androidTestImplementation(libs.compose.ui.test.junit4)

    // Jetpack compose UI tests manifest - Needed for createComposeRule, but not createAndroidComposeRule
    debugImplementation(libs.compose.ui.test.manifest)
}
