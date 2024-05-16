plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlinx.kover)
}

android {
    namespace = "com.makeappssimple.abhimanyu.financemanager.android.core.testing"
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
    implementation(project(":core:alarmkit"))
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:logger"))
    implementation(project(":core:model"))

    // Coroutines
    implementation(libs.coroutines.android)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Detekt compose
    detektPlugins(libs.detekt.rules.compose.nlopez)
    detektPlugins(project(":rules"))

    // Testing

    // JUnit
    implementation(libs.junit)

    // Android X test
    implementation(libs.test.core)
    implementation(libs.test.junit)
    implementation(libs.test.truth)
    implementation(libs.test.runner)
    implementation(libs.test.rules)

    // Mockito kotlin
    implementation(libs.mockito.kotlin)

    // Coroutines test
    implementation(libs.coroutines.test)

    // Hilt testing
    implementation(libs.hilt.android.testing)
    ksp(libs.hilt.android.compiler)
}
