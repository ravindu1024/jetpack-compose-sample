package com.ravindu1024.newsbrowser.ui.screens

import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ravindu1024.newsbrowser.model.domain.NewsHeadline
import com.ravindu1024.newsbrowser.ui.components.HeadlineRow
import com.ravindu1024.newsbrowser.ui.components.PullRefreshLazyList
import com.ravindu1024.newsbrowser.ui.state.HeadlinesUiState
import com.ravindu1024.newsbrowser.ui.state.TopBarAction
import com.ravindu1024.newsbrowser.ui.viewmodels.HeadlineViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun HeadLineScreen(
    barActions: (String, List<TopBarAction>) -> Unit,
    onItemCLicked: (NewsHeadline) -> Unit,
    viewModel: HeadlineViewModel = hiltViewModel()
) {
    // setup app bar
    barActions("Headlines", emptyList())


    val uiState by viewModel.uiState.collectAsState()

    val pullRefreshState = rememberPullToRefreshState()
    if (pullRefreshState.isRefreshing) {
        viewModel.getHeadlines()
    }

    if (!uiState.isLoading) {
        pullRefreshState.endRefresh()
    }

    // Entire screen UI is in this Composable. This is separated so it's easy to preview
    HeadlineScreenContent(pullRefreshState = pullRefreshState, uiState = uiState) {
        onItemCLicked(it)
    }


    remember {
        viewModel.getHeadlines()
        return@remember 0
    }
}


@Composable
fun HeadlineScreenContent(
    pullRefreshState: PullToRefreshState,
    uiState: HeadlinesUiState,
    onItemCLicked: (NewsHeadline) -> Unit
) {
    PullRefreshLazyList(
        items = uiState.headlines,
        pullRefreshState = pullRefreshState,
        isLoading = uiState.isLoading,
        rowProvider = { item ->
            HeadlineRow(
                headline = item,
                onItemClick = { onItemCLicked(it) }
            )
        }
    )
}


@Preview
@Composable
fun HeadlineScreenContentPreview() {
    val headlines = listOf(
        NewsHeadline(
            source = "ABC News",
            author = "John Doe",
            title = "Hello world",
            description = "skldvsdkjv sakljvksdjvn aksljdnvksajdnv asdkljvn",
            urlToImage = "dkhvdb",
            url = "cdd",
            publishedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        )
    )
    val refreshState = PullToRefreshState(positionalThresholdPx = 0f)
    val uiState = HeadlinesUiState(headlines = headlines)
    HeadlineScreenContent(
        pullRefreshState = refreshState,
        uiState = uiState,
        onItemCLicked = {}
    )
}