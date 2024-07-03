package com.ravindu1024.newsbrowser.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.ravindu1024.newsbrowser.ui.theme.NewsBrowserTheme

@Composable
fun <T> PullRefreshLazyList(
    items: List<T>,
    pullRefreshState: PullToRefreshState?,
    isLoading: Boolean,
    rowProvider: @Composable (T) -> Unit
) {
    Box(
        modifier = if (pullRefreshState != null) {
            Modifier
                .nestedScroll(pullRefreshState.nestedScrollConnection)
                .fillMaxSize()
        } else {
            Modifier
                .fillMaxSize()
        }
    ) {
        LazyColumn {
            items(items) { item ->
                rowProvider(item)
            }
        }

        if (pullRefreshState != null) {
            if (isLoading) {
                ListRefreshIndicator(Modifier.align(Alignment.TopCenter))
            }

            PullToRefreshContainer(
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Preview
@Composable
fun HeadlineLazyListPreview() {
    val items = listOf("item 1", "item 2")
    val refreshState = PullToRefreshState(positionalThresholdPx = 0f)
    NewsBrowserTheme {
        PullRefreshLazyList(items = items, pullRefreshState = refreshState, isLoading = false) {
            Text(text = it)
        }
    }
}