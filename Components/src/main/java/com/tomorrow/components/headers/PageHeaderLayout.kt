package com.tomorrow.components.headers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.tomorrow.components.others.AppSeparator
import com.tomorrow.components.buttons.BackButton

/**
 * if no function was passed for onBackPress
 * the button will not be displayed
 * */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageHeaderLayout(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    onBackPress: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    titleStyle: TextStyle = MaterialTheme.typography.headlineSmall,
    subtitleStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.secondary),
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit,
) = Column(modifier.fillMaxHeight()) {
    CompositionLocalProvider(
        LocalDensity provides Density(LocalDensity.current.density, 1f)
    ) {
        TopAppBar(
            title = {
                Column(Modifier) {
                    title?.let {
                        Row(
                            Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = it, style = titleStyle, maxLines = 1)
                        }
                    }
                    subtitle?.let {
                        Text(
                            text = it,
                            style = subtitleStyle,
                            maxLines = 1
                        )
                    }
                }
            },
            modifier = Modifier.padding(top = 15.dp),
            navigationIcon = {
                onBackPress?.let {
                    BackButton(onClick = { it() })
                }
            },
            actions = actions,
            colors = colors
        )
    }

    Column(modifier = Modifier.padding(contentPadding)) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PageHeaderLayoutPreview() {
    PageHeaderLayout(
        title = "Title",
        subtitle = "Subtitle , Subtitle Subtitle SubtitleSubtitle Subtitle Subtitle Subtitle v Subtitle v ",
        onBackPress = {},
        actions = {
            Text("Right corner content")
        },
        content = {
            AppSeparator(Modifier.fillMaxSize())
        }
    )

}