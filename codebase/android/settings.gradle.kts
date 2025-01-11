pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        mavenLocal()
        maven("https://storage.googleapis.com/r8-releases/raw")
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://jitpack.io")
    }
}

rootProject.name = "Finance Manager"
include(":app")
include(":app-ui-catalog")
include(":core")
include(":feature")
include(":konsist-test")
include(":lint")
include(":rules")
include(":ui-test-hilt-manifest")
