plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.about.libraries)
}

// region Kover
/*
kover {
    // true to disable instrumentation of all Kover tasks in this project
    isDisabled.set(false)

    // change instrumentation agent and reporter
    coverageEngine.set(kotlinx.kover.api.CoverageEngine.INTELLIJ)

    intellijEngineVersion.set("1.0.668")    // change version of IntelliJ agent and reporter
    jacocoEngineVersion.set("0.8.7")        // change version of JaCoCo agent and reporter

    // false to do not execute `koverMergedReport` task before `check` task
    generateReportOnCheck = true

    // ["project-name"] or [":project-name"] to disable coverage for project with path `:project-name` (`:` for the root project)
    disabledProjects = []

    // true to instrument packages `android.*` and `com.android.*`
    instrumentAndroidPackage = false

    // true to run all tests in all projects if `koverHtmlReport`, `koverXmlReport`, `koverReport`, `koverVerify` or `check` tasks executed on some project
}
*/

/*
TODO(Abhi): Fix kover
tasks.koverHtmlReport {
    excludes = listOf(
        // Hilt
        "*.di.*",
        "dagger.hilt.**",
        "hilt_aggregated_deps.*",
        "com.makeappssimple.abhimanyu.financemanager.android.*.*_Factory",

        // Room
        // MyRoomDatabase_AutoMigration_*_Impl, *Dao_Impl
        "com.makeappssimple.abhimanyu.financemanager.android.*.*_Impl*",

        // BuildConfig
        "com.makeappssimple.abhimanyu.financemanager.android.BuildConfig",

        // Moshi - Json Adapter
        "com.makeappssimple.abhimanyu.financemanager.android.*.*JsonAdapter",

        // UI
        "com.makeappssimple.abhimanyu.financemanager.android.ui.*"
    )
}
*/
// endregion

android {
    namespace = "com.makeappssimple.abhimanyu.financemanager.android"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = "com.makeappssimple.abhimanyu.financemanager.android"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = libs.versions.app.version.code.get().toInt()
        versionName = libs.versions.app.version.name.get()

        testInstrumentationRunner =
            "com.makeappssimple.abhimanyu.financemanager.android.core.testing.MyTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Room schema
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas".toString())
            }
        }
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            isTestCoverageEnabled = false
        }
    }

    // Kover
    // TODO(Abhi): Fix kover later
    /*
    testOptions {
//        unitTests {
//            returnDefaultValues = true
//        }

        unitTests.all {
            if (name == "testDebugUnitTest") {
                kover {
                    // disabled = false
                    // binaryReportFile.set(file("$buildDir/custom/debug-report.bin"))
                    includes = listOf(
                        "com.makeappssimple.abhimanyu.financemanager.android.*"
                    )
                    excludes = listOf(
                        "com.makeappssimple.abhimanyu.financemanager.android.ui.*",
                        "com.makeappssimple.abhimanyu.financemanager.android.navigation.di.NavigationManagerModule"
                    )
                }
            }
        }
    }
    */

    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true

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

        // Removed in Kotlin 1.7
        // Reference - https://stackoverflow.com/a/72864142/9636037
        // useIR = true

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
            // "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",

            // Compose
            "-opt-in=androidx.compose.runtime.ExperimentalComposeApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            // "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
            "-opt-in=androidx.compose.animation.core.InternalAnimationApi",
        )
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12" // jetpackComposeCompilerVersion
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(project(":core:alarmkit"))
    implementation(project(":core:alarmkitimpl"))
    implementation(project(":core:appkit"))
    implementation(project(":core:boot"))
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:logger"))
    implementation(project(":core:navigation"))
    implementation(project(":core:notificationkit"))
    implementation(project(":core:notificationkitimpl"))
    implementation(project(":core:time"))
    implementation(project(":core:ui"))
    implementation(project(":feature:accounts"))
    implementation(project(":feature:analysis"))
    implementation(project(":feature:categories"))
    implementation(project(":feature:home"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:transactionfor"))
    implementation(project(":feature:transactions"))

    debugImplementation(project(":ui-test-hilt-manifest"))

    testImplementation(project(":core:data-test"))
    testImplementation(project(":core:database-test"))
    testImplementation(project(":core:testing"))

    androidTestImplementation(project(":core:data-test"))
    androidTestImplementation(project(":core:database-test"))
    androidTestImplementation(project(":core:testing"))
    androidTestImplementation(project(":ui-test-hilt-manifest"))

    lintChecks(project(":lint"))

    implementation(libs.androidx.core)
    implementation(libs.lifecycle.runtime.core)
    implementation(libs.lifecycle.runtime.compose)

    // Jetpack compose
    implementation(libs.bundles.compose)

    // Jetpack compose material design adapter
    implementation(libs.material.compose.theme.adapter)

    // Jetpack compose integration with activities
    implementation(libs.activity.compose)

    // Jetpack compose integration with view models
    implementation(libs.lifecycle.viewmodel.compose)

    // Jetpack compose navigation
    implementation(libs.navigation.compose)

    // Jetpack compose navigation with hilt
    implementation(libs.hilt.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // KSP
    implementation(libs.ksp)

    // JDK desugaring
    coreLibraryDesugaring(libs.jdk.desugaring)

    // Data store
    implementation(libs.datastore)

    // Room
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)
    androidTestImplementation(libs.room.testing)

    // Firebase BoM
    implementation(platform(libs.firebase))

    // Firebase analytics
    implementation(libs.firebase.analytics)

    // Firebase crashlytics
    implementation(libs.firebase.crashlytics)

    detektPlugins(libs.bundles.detekt)

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

    // Turbine
    testImplementation(libs.turbine)

    // Hilt testing
    testImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)

    // Jetpack compose UI tests
    androidTestImplementation(libs.compose.ui.test.junit4)

    // KakaoCup Compose
    androidTestImplementation(libs.kakaocup.compose)

    // Jetpack compose UI tests manifest - Needed for createComposeRule, but not createAndroidComposeRule
    debugImplementation(libs.compose.ui.test.manifest)

    // Jetpack compose navigation testing
    implementation(libs.navigation.testing.compose)
}

kover {
    instrumentation {
        excludeTasks.add("testReleaseUnitTest")
    }
}

// region Detekt
/*
// TODO(Abhi): Check how to enable
tasks.withType(Detekt).configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
        txt.required.set(false)
        sarif.required.set(false)
        md.required.set(false)
    }
}
*/
// endregion
