pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
        maven("https://jitpack.io")
    }
}
dependencyResolutionManagement {
    versionCatalogs {
        create("libraries") {
            from(files("gradle/dependencies.versions.toml"))
        }
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://jitpack.io")
    }

}
rootProject.name = "uiComponents"
//demoApp
include(":app")
//modules
include(":EventListing")
include(":Components")
include(":ListDisplay")
include(":Carousel")
include(":QrCode")
include(":VideoPlayer")
