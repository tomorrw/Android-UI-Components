package com.tomorrow.eventlisting

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tomorrow.eventlisting.EventCard

@Composable
fun EventsLoader() {
    LazyColumn {
        item { Spacer(Modifier.height(16.dp)) }

        items(5) {
            EventCard(event = null)
            Spacer(Modifier.size(32.dp))
        }
    }
}
