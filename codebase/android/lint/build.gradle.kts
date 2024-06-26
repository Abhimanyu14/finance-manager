plugins {
    id("java-library")
    id("com.android.lint")
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

lint {
    htmlReport = true
    htmlOutput = file("lint/outputs/lint-report.html")
    textReport = true
    absolutePaths = false
    ignoreTestSources = true
}

dependencies {
    compileOnly(libs.lint.api)
    compileOnly(libs.lint.checks)
}
