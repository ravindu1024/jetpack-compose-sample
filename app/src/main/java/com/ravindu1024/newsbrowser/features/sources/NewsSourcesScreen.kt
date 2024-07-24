package com.ravindu1024.newsbrowser.features.sources

import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ravindu1024.domain.model.NewsSource
import com.ravindu1024.newsbrowser.ui.components.basic.TopBarAction
import com.ravindu1024.newsbrowser.ui.components.composite.NewsSourceRow
import com.ravindu1024.newsbrowser.ui.components.composite.PullRefreshLazyList

@Composable
fun NewsSourcesScreen(
    barActions: (String, List<TopBarAction>) -> Unit,
    viewModel: SourcesViewModel = hiltViewModel()
) {
    // setup app bar
    barActions("News Sources", emptyList())

    val uiState by viewModel.uiState.collectAsState()


    val pullRefreshState = rememberPullToRefreshState()
    if (pullRefreshState.isRefreshing) {
        viewModel.getSources()
    }

    if (!uiState.isLoading) {
        pullRefreshState.endRefresh()
    }

    NewsSourcesScreenContent(
        uiState = uiState,
        pullRefreshState = pullRefreshState,
        onSwitchClicked = { checked, source ->
            viewModel.updateSource(source.id, checked)
        }
    )

    remember {
        viewModel.getSources()
        return@remember 0
    }
}

@Composable
fun NewsSourcesScreenContent(
    uiState: SourcesListUiState,
    pullRefreshState: PullToRefreshState,
    modifier: Modifier = Modifier,
    onSwitchClicked: (Boolean, NewsSource) -> Unit,
) {
    PullRefreshLazyList(
        items = uiState.sources,
        pullRefreshState = pullRefreshState,
        isLoading = uiState.isLoading,
        modifier = modifier,
        rowProvider = { item ->
            NewsSourceRow(
                source = item,
                isChecked = uiState.savedSources.contains(item.id),
                onSwitchClicked = { checked, source ->
                    onSwitchClicked(checked, source)
                }
            )
        }
    )
}

@Preview
@Composable
fun NewsSourcesScreenContentPreview() {
    val sources = listOf(
        NewsSource(id = "", name = "ABC"),
        NewsSource(id = "", name = "The Guardian"),
        NewsSource(id = "", name = "ljvnskldj skljnvkljsdnv skljvnskljv \nsljnvklsjvn\n slnvsjklv")
    )
    val uiState = SourcesListUiState(sources = sources)
    NewsSourcesScreenContent(
        pullRefreshState = PullToRefreshState(positionalThresholdPx = 0f),
        uiState = uiState,
        onSwitchClicked = { _, _ -> })
}
