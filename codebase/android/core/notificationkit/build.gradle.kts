plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlinx.kover)
}

android {
    namespace = "com.makeappssimple.abhimanyu.financemanager.android.core.notificationkit"
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
        )
    }
}

dependencies {
    // Androidx core
    implementation(libs.androidx.core)

    // Appcompat
    implementation(libs.appcompat)

    // Detekt compose
    detektPlugins(libs.detekt.rules.compose.nlopez)
}
