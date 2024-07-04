package com.ravindu1024.newsbrowser.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ravindu1024.domain.model.NewsHeadline
import com.ravindu1024.newsbrowser.ui.components.HeadlineRow
import com.ravindu1024.newsbrowser.ui.components.PullRefreshLazyList
import com.ravindu1024.newsbrowser.ui.state.SavedHeadlinesUiState
import com.ravindu1024.newsbrowser.ui.state.TopBarAction
import com.ravindu1024.newsbrowser.ui.viewmodels.SavedHeadlinesViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun SavedHeadLinesScreen(
    barActions: (String, List<TopBarAction>) -> Unit,
    onItemCLicked: (NewsHeadline) -> Unit,
    viewModel: SavedHeadlinesViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    // setup app bar
    barActions("Saved Articles", emptyList())

    SavedHeadlineScreenContent(uiState = uiState) {
        onItemCLicked(it)
    }

    remember {
        viewModel.getSavedHeadlines()
        return@remember 0
    }
}

@Composable
fun SavedHeadlineScreenContent(
    uiState: SavedHeadlinesUiState,
    onItemCLicked: (NewsHeadline) -> Unit
){
    PullRefreshLazyList(
        items = uiState.savedHeadlines,
        pullRefreshState = null,
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
fun SavedHeadlineScreenContentPreview(){
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
    val uiState = SavedHeadlinesUiState(savedHeadlines = headlines)
    SavedHeadlineScreenContent(
        uiState = uiState,
        onItemCLicked = {}
    )

}

