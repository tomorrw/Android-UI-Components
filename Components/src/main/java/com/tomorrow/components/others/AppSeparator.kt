package com.tomorrow.components.others

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun AppSeparator(modifier: Modifier) {
    Spacer(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .height(height = 2.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )
}