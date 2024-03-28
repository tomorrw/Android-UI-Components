package com.tomorrow.components.others

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tomorrow.components.R

open class SocialLink(val platform: SocialPlatform, val link: String?)

fun List<SocialLink>.ensureSize(size: Int): List<SocialLink> {
    if (this.size == size) return this

    if (this.size < size) {
        var socialLinkPool = SocialPlatform.values().toMutableList()
            .filter { platform -> this.none { it.platform == platform } }

        return this + (1..(size - this.size)).map {
            if (socialLinkPool.isEmpty()) {
                socialLinkPool = SocialPlatform.values().toMutableList()
            }
            val link = socialLinkPool.random()
            socialLinkPool.minus(link)
            SocialLink(link, null)
        }
    }

    val newList = this.filter { it.link != null }

    return if (newList.size > 5) newList.take(5)
    else newList.ensureSize(5)
}

enum class SocialPlatform(val icon: Int) {
    Website(R.drawable.ic_website_socials),
    Facebook(R.drawable.ic_facebook_socials),
    LinkedIn(R.drawable.ic_linked_in_socials),
    Instagram(R.drawable.ic_instagram_socials),
    WhatsApp(R.drawable.ic_whatsapp_socials),
    Phone(R.drawable.ic_phone_socials),
    Youtube(R.drawable.ic_youtube_socials),
    Tiktok(R.drawable.ic_tiktok_socials),
    Twitter(R.drawable.ic_twitter_socials);
}


@Composable
fun Socials(
    modifier: Modifier,
    links: List<SocialLink>
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        links.forEach {
            SocialButton(
                painter = painterResource(id = it.platform.icon),
                link = it.link
            )
        }
    }
}

@Composable
private fun SocialButton(painter: Painter, link: String?) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(48.dp)
            .background(MaterialTheme.colorScheme.outline.copy(if (link == null) 0.2f else 1f))
            .clickable {
                if (link == null) {
                    Toast
                        .makeText(context, "Not Available.", Toast.LENGTH_LONG)
                        .show()
                    return@clickable
                }

                try {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(link),
                    )
                    context.startActivity(intent)
                } catch (_: Exception) {
                    Toast
                        .makeText(context, "Something went wrong.", Toast.LENGTH_LONG)
                        .show()
                }
            }
    ) {
        Icon(
            tint = MaterialTheme.colorScheme.onSurface.copy(if (link != null) 1f else 0.3f),
            painter = painter,
            contentDescription = "social icon",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}