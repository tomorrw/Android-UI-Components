package com.tomorrow.components.cards

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

open class CardStyle(
    open val backgroundColor: Color,
    open val imageBackgroundColor: Color = Color.Transparent,
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
    val tagBackgroundColor: Color? = null,
) : CardStyle(
    backgroundColor = backgroundColor,
    titleStyle = titleStyle,
    descriptionStyle = subtitleStyle
)

object CardStyleDefault {
    @Composable
    fun eventCardStyleDefault(
        backgroundColor: Color = MaterialTheme.colorScheme.background,
        timingStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.surfaceVariant, letterSpacing = 0.8.sp),
        titleStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary),
        subtitleStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.secondary),
        iconColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        tagStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
    ) = EventCardStyle(
        backgroundColor = backgroundColor,
        timingStyle = timingStyle,
        titleStyle = titleStyle,
        subtitleStyle = subtitleStyle,
        iconColor = iconColor,
        tagStyle = tagStyle,
    )

    @Composable
    fun inlineHighlightedCardDefaultStyle(
        backgroundColor: Color = MaterialTheme.colorScheme.onPrimary,
        imageBackgroundColor: Color = MaterialTheme.colorScheme.background,
        titleStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp
        ),
        descriptionStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(
            color = (Color(0xFF6594BD)),
            fontSize = 14.sp
        )
    ) = CardStyle(
        backgroundColor = backgroundColor,
        imageBackgroundColor = imageBackgroundColor,
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle
    )

    @Composable
    fun inlineCardDisplayStyle(
        backgroundColor: Color = MaterialTheme.colorScheme.background,
        imageBackgroundColor: Color = MaterialTheme.colorScheme.surface,
        titleStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary),
        descriptionStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.secondary)
    ) = CardStyle(
        backgroundColor = backgroundColor,
        imageBackgroundColor = imageBackgroundColor,
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle
    )
}