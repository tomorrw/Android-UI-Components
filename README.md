
# Android Ui Components

This is a collection of UI components for Android.

| Package Name | Description                |
| :--------    | :------------------------- |
| [`Carousel`](./Carousel)    | A collection of carousel |
| [`Components`](./Components)    | A selection of ui components and their presentation models... |
| [`EventListing`](./EventListing)    | A way to display a list of event cards |
| [`ListDisplay`](./ListDisplay)    | A customizable Searchable list |
| [`QrCode`](./QrCode)    | A QrCode generator |
| [`VideoPlayer`](./VideoPlayer)    | A Video Player Component |


## Install Package

## Install Package

```kotlin
implementation("com.tomorrow.Android-Ui-Components:carousel:$version")
implementation("com.tomorrow.Android-Ui-Components:components:$version")
implementation("com.tomorrow.Android-Ui-Components:eventlisting:$version")
implementation("com.tomorrow.Android-Ui-Components:listdisplay:$version")
implementation("com.tomorrow.Android-Ui-Components:qrcode:$version")
implementation("com.tomorrow.Android-Ui-Components:videoplayer:$version")
```
`❗️ Don't Forget to add this to your graddle project file`
```kotlin
val envVal = loadEnv("${project.rootProject.projectDir}/.env")
maven {
    name = it
    url = uri("https://maven.pkg.github.com/tomorrw/Android-UI-Components")
    credentials {
        username = envValues['USERNAME']
        password = envValues['TOKEN']
    }
}

// a load func 
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
```
`❗ Don't Forget to configure your env file`
```
USERNAME = Your GitHub UserName goes here
TOKEN = Generate a github token and paste it it
```
