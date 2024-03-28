package com.tomorrow.components.cards

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

open class CardStyle(
    open val backgroundColor: Color,
    open val imageBackgroundColor: Color,
    open val titleStyle: TextStyle,
    open val descriptionStyle: TextStyle
)

data class EventCardStyle(
    override val backgroundColor: Color,
    val timingStyle: TextStyle,
    override val titleStyle: TextStyle,
    val subtitleStyle: TextStyle,
    val iconColor: Color,
    val tagStyle: TextStyle,
    val speakerTextStyle: TextStyle,
    override val imageBackgroundColor: Color
): CardStyle(
    backgroundColor = backgroundColor,
    imageBackgroundColor = imageBackgroundColor,
    titleStyle = titleStyle,
    descriptionStyle = subtitleStyle
)

object CardStyleDefault{
    @Composable
    fun eventCardStyleDefault() = EventCardStyle(
        backgroundColor = MaterialTheme.colorScheme.background,
        timingStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.surfaceVariant),
        titleStyle = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary),
        subtitleStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.secondary),
        iconColor = MaterialTheme.colorScheme.surfaceVariant,
        tagStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
        speakerTextStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
        imageBackgroundColor = MaterialTheme.colorScheme.surface
    )
}