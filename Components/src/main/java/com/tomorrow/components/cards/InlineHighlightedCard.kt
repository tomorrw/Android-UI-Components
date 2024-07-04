package com.tomorrow.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun InlineHighlightedCard(
    modifier: Modifier = Modifier,
    name: String,
    avatar: String? = null,
    detailText: String? = null,
    onClick: () -> Unit = {},
    style: CardStyle = CardStyleDefault.inlineHighlightedCardDefaultStyle()
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .clickable { onClick() }
            .background(style.backgroundColor)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            avatar?.let {
                Box(
                    modifier = Modifier
                        .padding(14.dp)
                        .clip(CircleShape)
                        .size(75.dp)
                        .border(0.3.dp, Color(0xFFDAE6F1), CircleShape)
                        .background(
                            shape = CircleShape,
                            color = if (avatar.isNotEmpty())
                                style.imageBackgroundColor
                            else
                                Color(0xFFD3E2F0)
                        ),
                ) {
                    AsyncImage(
                        model = avatar,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = name,
                    style = style.titleStyle,
                    maxLines = 2,
                )

                detailText?.let {
                    Text(
                        text = it,
                        style = style.descriptionStyle,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Composable
@Preview()
private fun InlineCardPreview() {
    InlineHighlightedCard(
        name = "John Doe",
        avatar = "https://i.pravatar.cc/300",
        detailText = "Patient",
        onClick = {}
    )
}
