package com.tomorrow.eventlisting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tomorrow.components.cards.CardStyleDefault
import com.tomorrow.eventlisting.presentationModel.EventCardModel
import java.time.format.DateTimeFormatter
import com.tomorrow.components.cards.EventCard as Card

private val formatter = DateTimeFormatter.ofPattern("HH:mm")
private val dayFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d")


@Composable
fun EventCard(modifier: Modifier = Modifier, event: EventCardModel?, rightIcon: @Composable (String) -> Unit = {}, cardFooter: @Composable () -> Unit = {}) {

    event?.let {
        val tag = it.getTag()
        Card(
            modifier = modifier,
            eventName = event.title,
            topic = event.topic,
            startTime = event.startDate.format(formatter),
            endTime = event.endDate.format(formatter),
            day = event.startDate.format(dayFormatter),
            location = event.location,
            cardFooter = cardFooter,
            tag = tag?.text,
            onClick = { event.onClick(event.id) },
            rightIcon = { rightIcon(event.id) },
            styles = CardStyleDefault.eventCardStyleDefault().copy(
                backgroundColor = event.color ?: MaterialTheme.colorScheme.background,
            ),
        )
    } ?: Box(
        Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(180.dp)
            .background(Color(0xFFD3E2F0))
    )
}

