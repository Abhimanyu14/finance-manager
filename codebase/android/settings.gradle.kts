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
include(":core:alarmkit")
include(":core:alarmkitimpl")
include(":core:appkit")
include(":core:boot")
include(":core:common")
include(":core:configkit")
include(":core:configkitimpl")
include(":core:data")
include(":core:data-test")
include(":core:database")
include(":core:database-test")
include(":core:datastore")
include(":core:designsystem")
include(":core:logger")
include(":core:model")
include(":core:navigation")
include(":core:network")
include(":core:notificationkit")
include(":core:notificationkitimpl")
include(":core:testing")
include(":core:time")
include(":core:ui")
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
