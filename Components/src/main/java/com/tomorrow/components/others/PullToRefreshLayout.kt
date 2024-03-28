package com.tomorrow.components.others

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullToRefreshLayout(
    state: PullRefreshState,
    isRefreshing: Boolean,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = MaterialTheme.colors.secondary,
    contentSlot: @Composable () -> Unit,
    ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds()
            .pullRefresh(state)
    ) {
        contentSlot()

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            refreshing = isRefreshing,
            state = state,
        )
    }
}