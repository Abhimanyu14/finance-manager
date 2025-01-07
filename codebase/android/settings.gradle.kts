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
include(":chart")
include(":cre")
include(":legacycore:alarmkit")
include(":legacycore:alarmkitimpl")
include(":legacycore:appkit")
include(":legacycore:boot")
include(":legacycore:common")
include(":legacycore:configkit")
include(":legacycore:configkitimpl")
include(":legacycore:data")
include(":legacycore:data-test")
include(":legacycore:database")
include(":legacycore:database-test")
include(":legacycore:datastore")
include(":legacycore:designsystem")
include(":legacycore:logger")
include(":legacycore:model")
include(":legacycore:navigation")
include(":legacycore:network")
include(":legacycore:notificationkit")
include(":legacycore:notificationkitimpl")
include(":legacycore:testing")
include(":legacycore:time")
include(":legacycore:ui")
include(":feature:accounts")
include(":feature:analysis")
include(":feature:categories")
include(":feature:home")
include(":feature:settings")
include(":feature:transactionfor")
include(":feature:transactions")
include(":konsist-test")
include(":lint")
include(":rules")
include(":ui-test-hilt-manifest")
