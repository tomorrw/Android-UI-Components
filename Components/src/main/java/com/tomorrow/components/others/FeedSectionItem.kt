package com.tomorrow.components.others

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun FeedSectionItem(
    modifier: Modifier? = Modifier,
    albumArt: @Composable () -> Unit,
    title: @Composable () -> Unit,
) {
    Column(
        modifier = modifier ?: Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        albumArt()

        title()
    }
}
