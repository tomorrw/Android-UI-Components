package com.tomorrow.components.others

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tomorrow.components.R

open class PersonPresentationModel(
    val id: String,
    val fullName: String,
    val image: String?,
    val countryFlag: String?,
    val onClick: (String) -> Unit
)

@Composable
fun PersonDisplay(person: PersonPresentationModel) {
    val interactionSource = remember { MutableInteractionSource() }
    FeedSectionItem(
        modifier = Modifier
            .clickable(
                interactionSource,
                null
            ) { person.onClick(person.id) }
            .height(100.dp)
            .width(84.dp),
        albumArt = {
            Box {
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(56.dp)
                        .background(MaterialTheme.colorScheme.background),
                    model = person.image ?: "",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.ic_avatar_placeholder)
                )
                AsyncImage(
                    person.countryFlag,
                    contentDescription = "country",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(16.dp)
                        .align(Alignment.BottomStart)
                        .background(MaterialTheme.colorScheme.background)
                        .border(1.dp, MaterialTheme.colorScheme.primary.copy(0.4f), CircleShape),
                )
            }
        },
        title = {
            Text(
                person.fullName,
                style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
                modifier = Modifier.padding(top = 6.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    )
}