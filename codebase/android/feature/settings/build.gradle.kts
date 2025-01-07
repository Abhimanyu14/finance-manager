plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.makeappssimple.abhimanyu.financemanager.android.feature.settings"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner =
            "com.makeappssimple.abhimanyu.financemanager.android.cre.testing.MyTestRunner"
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
            "-opt-in=androidx.compose.animation.cre.InternalAnimationApi",
        )
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":core"))
    testImplementation(project(":legacycore:testing"))

    androidTestImplementation(project(":legacycore:testing"))

    lintChecks(project(":lint"))

    implementation(libs.androidx.core)
    implementation(libs.lifecycle.runtime.core)

    // Coroutines
    implementation(libs.coroutines.android)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Jetpack compose
    implementation(libs.bundles.compose)

    // Jetpack compose integration with view models
    implementation(libs.lifecycle.viewmodel.compose)

    // Jetpack compose lifecycle runtime
    implementation(libs.lifecycle.runtime.compose)

    // Jetpack compose navigation
    implementation(libs.navigation.compose)

    // Jetpack compose navigation with hilt
    implementation(libs.hilt.navigation.compose)

    // About libraries
    implementation(libs.about.libraries.core)
    implementation(libs.about.libraries.compose)

    // KotlinX collections immutable
    implementation(libs.collections.immutable)

    // Firebase BoM
    implementation(platform(libs.firebase))

    // Firebase perf
    implementation(libs.firebase.perf)

    // Detekt
    detektPlugins(libs.bundles.detekt)

    // Testing

    // JUnit
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit)

    // Android X test
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.test)

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
    kspTest(libs.hilt.compiler)
    kspAndroidTest(libs.hilt.compiler)

    // Jetpack compose UI tests
    androidTestImplementation(libs.compose.ui.test.junit4)

    // KakaoCup Compose
    androidTestImplementation(libs.kakaocup.compose)

    // Jetpack compose UI tests manifest - Needed for createComposeRule, but not createAndroidComposeRule
    debugImplementation(libs.compose.ui.test.manifest)
}
