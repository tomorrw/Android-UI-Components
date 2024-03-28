package com.tomorrow.eventlisting.presentationModel

import androidx.compose.ui.graphics.Color
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
    open val hasAttended: Boolean = false,
    open val color: Color? = null,
    open val onClick: (id: String) -> Unit = {},
)

fun EventCardModel.isNow(): Boolean {
    val now = LocalDateTime.now()
    return now.isAfter(this.startDate) && now.isBefore(this.endDate)
}
