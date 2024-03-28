# Video Player

A custom video player for Jetpack Compose.

## Usage
```kotlin
import com.tomorrow.videoplayer.VideoPlayer
```


## Components
### Video Player 

```kotlin
VideoPlayer(
    modifier: Modifier,
    videoUrl: String,
    viewModel: VideoPlayerViewModel,
    fullScreenViewModel: FullScreenViewModel
)
```
### View Models
`💡Define your view models in a dependency injection framework like Hilt or Koin to be able to access your view models accross your application.`

#### VideoPlayerViewModel
```kotlin
VideoPlayerViewModel(
    application: Application
)
```
---
#### FullScreenViewModel
```kotlin
FullScreenViewModel()
```
`❗️Don't forget to place the fullScreenViewModel.content() into the parent composable of your App.`
```kotlin
Box(Modifier.fillMaxSize()) {
     fullScreenViewModel.content()
}
```
