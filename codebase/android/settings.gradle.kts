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
include(":chart")
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
include(":legacyfeature:accounts")
include(":legacyfeature:analysis")
include(":legacyfeature:categories")
include(":legacyfeature:home")
include(":legacyfeature:settings")
include(":legacyfeature:transactionfor")
include(":legacyfeature:transactions")
include(":konsist-test")
include(":lint")
include(":rules")
include(":ui-test-hilt-manifest")
include(":feature")
