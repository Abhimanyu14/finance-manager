import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

// TODO(Abhi): Revisit the "apply"
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    // alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.about.libraries) apply false
}

// region Kover
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
// endregion

// region Detekt
detekt {
    toolVersion = "1.23.6"
    config.setFrom("$projectDir/config/detekt./detekt.yml")
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
        xml.required.set(false) // checkstyle like format mainly for integrations like Jenkins
        txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
        sarif.required.set(false) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with GitHub Code Scanning
        md.required.set(true) // simple Markdown format
    }
}

// Kotlin DSL
tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}
// endregion
