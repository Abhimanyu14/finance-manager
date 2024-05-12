buildscript {
    // App version code
    val appVersionCode = 116

    // App version name
    val appVersionName = "2024.05.12.0"

    // Compile SDK version
    val compileSdkVersion: Int = 34

    // Minimum SDK version
    val minSdkVersion = 27

    // Target SDK version
    val targetSdkVersion = 34

    // Jetpack compose compiler
    /**
     * Map according to to kotlin version from here,
     * https://developer.android.com/jetpack/androidx/releases/compose-kotlin
     */
    val jetpackComposeCompilerVersion = "1.5.12"
}

// TODO(Abhi): Revisit the "apply"
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.about.libraries) apply false
}

/*
// TODO(Abhi): Fix kover
koverMerged {
    enable()

    filters {
        classes {
            excludes.addAll(
                // Fragment
                "*Fragment",
                "*Fragment\$*",

                // Activity
                "*Activity",
                "*Activity\$*",

                // ViewBinding
                "*.databinding.*",

                // Hilt
                "dagger.hilt.**",
                "*HiltModules*",
                "hilt_aggregated_deps.*",
                "*.di.*",

                // Room
                "*Dao_Impl*",
                "*MyRoomDatabase_AutoMigration*",
                "*MyRoomDatabase_Impl*",

                // BuildConfig
                "*BuildConfig*",
            )
        }

        annotations {
            excludes.addAll(
                "*Composable",
                "*Preview",
                "*Generated",
            )
        }
    }
}
*/

detekt {
    toolVersion = "1.23.6"
    config.setFrom("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
//    reports {
//        xml.required.set(true)
//        html.required.set(true)
//        txt.required.set(false)
//        sarif.required.set(false)
//        md.required.set(false)
//    }
}

/*
// TODO(Abhi): Fix detekt
tasks.named("detekt").configure {
    reports {
        // Enable/Disable XML report (default: true)
        xml.required.set(true)
        xml.outputLocation.set(file("build/reports/detekt.xml"))
        // Enable/Disable HTML report (default: true)
        html.required.set(true)
        html.outputLocation.set(file("build/reports/detekt.html"))
        // Enable/Disable TXT report (default: true)
        txt.required.set(true)
        txt.outputLocation.set(file("build/reports/detekt.txt"))
        // Enable/Disable SARIF report (default: false)
        sarif.required.set(true)
        sarif.outputLocation.set(file("build/reports/detekt.sarif"))
        // Enable/Disable MD report (default: false)
        md.required.set(true)
        md.outputLocation.set(file("build/reports/detekt.md"))
        custom {
            // The simple class name of your custom report.
            reportId = "CustomJsonReport"
            outputLocation.set(file("build/reports/detekt.json"))
        }
    }
}
*/
