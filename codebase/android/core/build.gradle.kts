plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.makeappssimple.abhimanyu.financemanager.android.core"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

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
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",

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
        buildConfig = true
        compose = true
    }

    // Room schema for testing
    sourceSets {
        // Adds exported schema location as test app assets.
        getByName("androidTest").assets.srcDir("$projectDir/schemas")
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    lintChecks(project(":lint"))

    // Androidx core
    implementation(libs.androidx.core)
    implementation(libs.lifecycle.runtime.core)

    // Coroutines
    implementation(libs.coroutines.android)

    // Emoji2
    implementation(libs.emoji2)

    // Jetpack compose
    implementation(libs.bundles.compose)

    // Jetpack compose material icons
    implementation(libs.compose.icons)

    // Jetpack compose integration with activities
    implementation(libs.activity.compose)

    // Jetpack compose integration with view models
    implementation(libs.lifecycle.viewmodel.compose)

    // Jetpack compose lifecycle runtime
    implementation(libs.lifecycle.runtime.compose)

    // Jetpack compose navigation
    implementation(libs.navigation.compose)

    // Jetpack compose navigation with hilt
    implementation(libs.hilt.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.work)
    ksp(libs.hilt.compiler)

    // Compose emoji picker
    implementation(libs.compose.emoji.picker)

    // KSP
    implementation(libs.ksp)

    // Firebase BoM
    implementation(platform((libs.firebase)))

    // Firebase remote config
    implementation(libs.firebase.config)

    // KotlinX serialization
    implementation(libs.serialization)

    // KotlinX collections immutable
    implementation(libs.collections.immutable)

    // Okhttp logging interceptor
    implementation(libs.okhttp)

    // Data store
    implementation(libs.datastore)

    // Room
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)
    androidTestImplementation(libs.room.testing)

    // Work manager
    implementation(libs.work.runtime)
    androidTestImplementation(libs.work.testing)

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
    implementation(libs.hilt.android.testing)
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing)
    ksp(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)
    kspAndroidTest(libs.hilt.compiler)

    // Jetpack compose UI tests
    androidTestImplementation(libs.compose.ui.test.junit4)

    // KakaoCup Compose
    androidTestImplementation(libs.kakaocup.compose)

    // Jetpack compose UI tests manifest - Needed for createComposeRule, but not createAndroidComposeRule
    debugImplementation(libs.compose.ui.test.manifest)
}
