package com.tomorrow.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tomorrow.components.others.AppSeparator

@Composable
fun HighlightedCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    date: String? = null,
    image: String? = null,
    onClick: () -> Unit,
    style: CardStyle = CardStyle(
        backgroundColor = MaterialTheme.colorScheme.background,
        imageBackgroundColor = MaterialTheme.colorScheme.surface,
        titleStyle = MaterialTheme.typography.headlineSmall,
        descriptionStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.secondary, lineHeight = 18.sp)
    )
) = Box(
    modifier = modifier
        .clip(RoundedCornerShape(8.dp))
        .background(color = style.backgroundColor)
        .clickable { onClick() }
) {
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .padding(bottom = 18.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        image?.let {
            AsyncImage(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(style.imageBackgroundColor),
                model = it,
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = title,
            style = style.titleStyle
        )

        Column {
            AppSeparator(Modifier.width(72.dp).padding(vertical = 6.dp))
            Text(
                text = description,
                style = style.descriptionStyle
            )
            date?.let {
                Text(
                    text = it,
                    style = style.descriptionStyle
                )
            }
        }
    }
}

@Composable
@Preview()
fun HighlightedCardPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        HighlightedCard(
            title = "John Doe",
            image = "https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg",
            description = "https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg",
            date = "12 may 2021",
            onClick = {}
        )

    }
}

