# QrCode

A quick and easy way to generate and display QR codes.

## Usage
```kotlin
import com.tomorrow.qrcode.rememberQrBitmapPainter
```


## Components
### rememberQrBitmapPainter
#### Usage
```kotlin
rememberQrBitmapPainter(
    content: String,
    size: Dp = 150.dp,
    padding: Dp = 0.dp,
    background: UiColor = MaterialTheme.colorScheme.background,
    color: UiColor = MaterialTheme.colorScheme.onSurface,
): BitmapPainter
```