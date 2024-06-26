pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "swing"
include(":app")
include(":core:ui")
include(":core:model")
include(":core:data")
include(":core:datastore")
include(":core:database")
include(":core:network")
include(":core:common")

include(":feature:gallery")
include(":feature:favorite")
include(":feature:settings")
include(":feature:search")
