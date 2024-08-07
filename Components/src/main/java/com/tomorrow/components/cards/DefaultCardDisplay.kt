package com.tomorrow.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun DefaultCardDisplay(
    painter: Painter,
    onClick: () -> Unit,
    title: String,
    subtitle: String,
    style: CardStyle = CardStyleDefault.inlineCardDisplayStyle()
) = Row(
    Modifier
        .clip(RoundedCornerShape(8.dp))
        .background(style.backgroundColor)
        .fillMaxWidth()
        .clickable { onClick() },
    verticalAlignment = Alignment.CenterVertically,
) {
    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painter,
            modifier = Modifier.size(48.dp),
            contentDescription = "icon"
        )

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = title,
                style = style.titleStyle
            )
            Text(
                text = subtitle,
                style = style.descriptionStyle
            )
        }
    }
}
