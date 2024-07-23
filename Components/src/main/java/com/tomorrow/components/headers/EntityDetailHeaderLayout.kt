package com.tomorrow.components.headers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tomorrow.components.others.SocialLink
import com.tomorrow.components.others.Socials
import com.tomorrow.components.R
import com.tomorrow.components.buttons.BackButton
import com.tomorrow.components.buttons.ShareButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntityDetailHeaderLayout(
    title: String,
    subtitle: String?,
    image: String,
    socialLinks: List<SocialLink>?,
    decorativeIcon: (@Composable () -> Unit)? = null,
    onBack: () -> Unit,
    shareLink: String,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    content: @Composable () -> Unit,
) {
    val isImagePopupShowing = remember { mutableStateOf(false) }
    val avatarCoordinates = remember { mutableStateOf<LayoutCoordinates?>(null) }
    val avatarSize = 78.dp

    return Box {
        Column {
            TopAppBar(
                modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                title = {},
                navigationIcon = { BackButton(onClick = { onBack() }) },
                actions = { ShareButton(webLink = shareLink) },
            )

            Column(
                Modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.clickable {
                        isImagePopupShowing.value = !isImagePopupShowing.value
                    }) {
                    AsyncImage(
                        modifier = Modifier
                            .size(avatarSize)
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colorScheme.background)
                            .onGloballyPositioned {
                                avatarCoordinates.value = it
                            },
                        model = image,
                        contentDescription = "",
                        error = painterResource(id = R.drawable.ic_image_default_albumart),
                        contentScale = ContentScale.Crop,
                    )
                    decorativeIcon?.let {
                        Box(
                            modifier = Modifier
                                .size(32.dp)

                        ) { it() }
                    }
                }
                Row(
                    Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        title,
                        style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        maxLines = 2,
                    )
                }

                Spacer(Modifier.height(4.dp))

                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    socialLinks?.let {
                        Socials(
                            modifier = Modifier
                                .fillMaxWidth(socialLinks.size.toFloat() * 0.2f)
                                .padding(top = 24.dp),
                            links = it,
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            content()
        }


        AnimatedVisibility(
            visible = isImagePopupShowing.value,
            enter = fadeIn(tween(400)),
            exit = fadeOut(tween(400)),
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(backgroundColor.copy(alpha = 0.8f))
                    .clickable { isImagePopupShowing.value = !isImagePopupShowing.value },
                Alignment.TopCenter
            ) {}
        }
        Column(
            Modifier
                .fillMaxSize()
                .offset(y = LocalDensity.current.run {
                    avatarCoordinates.value?.positionInWindow()?.y?.toDp() ?: 0.dp
                }),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = isImagePopupShowing.value,
                enter = scaleIn(
                    animationSpec = tween(300),
                    initialScale = 0.25f,
                    transformOrigin = TransformOrigin(0.5f, 0f)
                ),
                exit = scaleOut(
                    animationSpec = tween(300),
                    targetScale = 0.25f,
                    transformOrigin = TransformOrigin(0.5f, 0f)
                ),

                ) {
                AsyncImage(
                    modifier = Modifier
                        .size(avatarSize)
                        .scale(4f)
                        .aspectRatio(1f)
                        .clip(if (isImagePopupShowing.value) RoundedCornerShape(12.dp) else CircleShape)
                        .background(color = MaterialTheme.colorScheme.background),
                    model = image,
                    contentDescription = "",
                    error = painterResource(id = R.drawable.ic_image_default_albumart),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}


@Composable
@Preview()
private fun EntityDetailHeaderPreview() {
    EntityDetailHeaderLayout(
        title = "Marc",
        subtitle = "test",
        image = "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp",
        socialLinks = listOf(),
        onBack = { /*TODO*/ },
        shareLink = "",
        decorativeIcon = {
            AsyncImage(
                modifier = Modifier
                    .size(78.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.background),
                model = "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp",
                contentDescription = "",
                error = painterResource(id = R.drawable.ic_image_default_albumart),
                contentScale = ContentScale.Crop,
            )
        }
    ) {
        androidx.compose.material.Divider(Modifier.fillMaxSize())
    }
}
