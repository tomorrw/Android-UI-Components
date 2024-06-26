package com.tomorrow.components.others

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TagText(
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(color = Color.White),
    backgroundColor: Color? = null,
    modifier: Modifier = Modifier
) = Text(
    modifier = modifier
        .clip(RoundedCornerShape(4.dp))
        .background(backgroundColor ?: MaterialTheme.colorScheme.primary.copy(0.6f))
        .padding(horizontal = 8.dp)
        .padding(vertical = 4.dp),
    text = text,
    style = textStyle
)