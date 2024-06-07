package com.tomorrow.eventlisting.presentationModel

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.tomorrow.components.cards.EventSpeaker
import java.time.LocalDateTime

open class EventCardModel(
    open val id: String = "123",
    open val startDate: LocalDateTime,
    open val endDate: LocalDateTime,
    open val title: String,
    open val topic: String?,
    open val speakers: List<EventSpeaker>,
    open val location: String,
    // the tag will update on recomposition
    open val getTag: @Composable () -> Tag?,
    open val color: Color? = null,
    open val onClick: (id: String) -> Unit = {},
    ) {
    data class Tag(
        val text: String,
        val textStyle: TextStyle,
        val backgroundColor: Color?
    )
}

fun EventCardModel.isNow(): Boolean {
    val now = LocalDateTime.now()
    return now.isAfter(this.startDate) && now.isBefore(this.endDate)
}
