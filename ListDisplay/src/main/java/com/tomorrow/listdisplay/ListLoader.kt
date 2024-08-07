package com.tomorrow.listdisplay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tomorrow.components.cards.InlineCardDisplay

@Composable
fun ListLoader() {
    LazyColumn {
        item { Spacer(Modifier.height(16.dp)) }

        items(5) {
            if (it != 0) Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
            InlineCardDisplay(avatar = "", name = "")
        }
    }
}