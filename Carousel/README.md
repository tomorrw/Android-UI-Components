# Carousel

A collection of carousel views for displaying events / ads...

## Usage
```kotlin
import com.tomorrow.carousel.AdCarousel
import com.tomorrow.carousel.Ad
import com.tomorrow.carousel.OnBoardingCarousel
import com.tomorrow.carousel.OnBoardingItem
```


## Components
### AdCarousel
#### Ad
```kotlin
Ad(
    val image: String?,
    val url: String,
)
```
#### Usage
```kotlin
AdCarousel(
    modifier: Modifier = Modifier,
    onClick: (url: String, context: Context) -> Unit,
    ads: List<Ad>,
)
```

### OnBoardingCarousel
#### OnBoardingItem
```kotlin
OnBoardingItem(
    val title: String,
    val body: String,
    val buttons: @Composable (ColumnScope) -> Unit,
)
```
#### Usage
```kotlin
OnBoardingCarousel(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    items: List<OnBoardingItem>,
)
```
