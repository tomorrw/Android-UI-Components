
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
implementation("com.github.tomorrw.Android-Ui-Components:carousel:$version")
implementation("com.github.tomorrw.Android-Ui-Components:components:$version")
implementation("com.github.tomorrw.Android-Ui-Components:eventlisting:$version")
implementation("com.github.tomorrw.Android-Ui-Components:listdisplay:$version")
implementation("com.github.tomorrw.Android-Ui-Components:qrcode:$version")
implementation("com.github.tomorrw.Android-Ui-Components:videoplayer:$version")
```
`❗️ Don't Forget to add`
```kotlin
maven {
    name = it
    url = uri("https://maven.pkg.github.com/tomorrw/Android-UI-Components")
    credentials {
        username = envValues['USERNAME']
        password = envValues['TOKEN']
    }
}
```