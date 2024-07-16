package com.tomorrow.components.cards


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tomorrow.components.others.TagText

@Composable
fun EventCard(
    modifier: Modifier = Modifier,
    eventName: String,
    topic: String?,
    startTime: String,
    endTime: String,
    location: String,
    day: String? = null,
    cardFooter: @Composable () -> Unit = {},
    onClick: () -> Unit,
    tag: String? = null,
    styles: EventCardStyle = CardStyleDefault.eventCardStyleDefault(),
    rightIcon: @Composable () -> Unit = { Spacer(modifier = Modifier) }
) = Box(modifier = modifier
    .clip(RoundedCornerShape(8.dp))
    .background(color = styles.backgroundColor)
    .clickable { onClick() }
    .padding(all = 16.dp)
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Column(
            modifier = Modifier.weight(0.9f)
        ) {
            Text(
                text = "$startTime - $endTime",
                style = styles.timingStyle
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = eventName,
                style = styles.titleStyle
            )

            topic?.let {
                Text(
                    text = it,
                    style = styles.subtitleStyle
                )
            }

            cardFooter()

            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.LocationOn,
                    contentDescription = "location",
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.CenterVertically),
                    tint = styles.iconColor
                )

                Text(
                    text = location,
                    style = styles.tagStyle,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            Spacer(Modifier.height(2.dp))
        }

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            rightIcon()
        }

    }
    Column(
        modifier = Modifier.align(Alignment.BottomEnd)
    ) {
        if (tag != null) TagText(
            text = tag,
            textStyle = styles.tagStyle,
            backgroundColor = styles.tagBackgroundColor
        ) else Spacer(Modifier)
    }
}

@Composable
@Preview()
fun EventCardPreview() {
    EventCard(
        eventName = "Event Name",
        topic = "Topic",
        startTime = "10:00",
        endTime = "11:00",
        location = "Lecture Hall",
        onClick = {}
    )
}