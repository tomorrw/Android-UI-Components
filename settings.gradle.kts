import java.nio.file.Paths

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
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
        val envVal = loadEnv(".env")
        if (!envVal["USERNAME"].isNullOrBlank() && !envVal["TOKEN"].isNullOrBlank()) {
            listOf("Android-Project-Startup").map {
                maven {
                    name = it
                    url = uri("https://maven.pkg.github.com/tomorrw/$it")
                    credentials {
                        username = envVal["USERNAME"]
                        password = envVal["TOKEN"]
                    }
                }
            }
        }
    }

}

fun loadEnv(filePath: String): Map<String, String> {
    val envMap = mutableMapOf<String, String>()
    val envFile = Paths.get(filePath).toFile()
    if (envFile.exists()) {
        envFile.forEachLine { line ->
            if (line.contains("=") && !line.startsWith("#")) {
                val (key, value) = line.split("=", limit = 2)
                envMap[key.trim()] = value.trim()
            }
        }
    }
    return envMap
}

rootProject.name = "uiComponents"
include(":EventListing")
include(":Components")
include(":ListDisplay")
include(":Carousel")
include(":QrCode")
include(":VideoPlayer")
//include(":app")
