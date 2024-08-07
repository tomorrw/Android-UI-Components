package com.tomorrow.components.others

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tomorrow.components.R
import com.tomorrow.components.headers.PageHeaderLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralError(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {
        Icon(
            modifier = Modifier.size(50.dp),
            painter = painterResource(R.drawable.baseline_error_outline_24),
            contentDescription = "Error Icon",
        )
    },
    message: String,
    description: String,
    buttonText: String? = "Try Again",
    onButtonClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    style: GeneralErrorStyle = GeneralErrorStyle(
        messageStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
        descriptionStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        ),
        buttonStyle = MaterialTheme.typography.titleMedium
    )
) {
    PageHeaderLayout(onBackPress = onBackClick, content = {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                icon()

                Spacer(Modifier.height(8.dp))

                Text(
                    message,
                    style = style.messageStyle
                )

                Text(
                    text = description,
                    style = style.descriptionStyle
                )

                onButtonClick?.let {
                    Button(
                        onClick = onButtonClick,
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .fillMaxWidth()
                            .heightIn(min = 50.dp),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(
                            buttonText ?: "Try Again",
                            style = style.buttonStyle
                        )
                    }
                }

                Spacer(Modifier.height(50.dp))
            }
        }
    })
}

data class GeneralErrorStyle(
    val messageStyle: TextStyle,
    val descriptionStyle: TextStyle,
    val buttonStyle: TextStyle,
)