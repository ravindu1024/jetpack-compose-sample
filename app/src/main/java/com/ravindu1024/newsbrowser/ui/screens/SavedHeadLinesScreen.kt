package com.ravindu1024.newsbrowser.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ravindu1024.newsbrowser.model.domain.NewsHeadline
import com.ravindu1024.newsbrowser.ui.components.HeadLineLazyList
import com.ravindu1024.newsbrowser.ui.components.ListRefreshIndicator
import com.ravindu1024.newsbrowser.ui.state.HeadlinesUiState
import com.ravindu1024.newsbrowser.ui.state.SavedHeadlinesUiState
import com.ravindu1024.newsbrowser.ui.state.TopBarUiState
import com.ravindu1024.newsbrowser.ui.viewmodels.SavedHeadlinesViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun SavedHeadLinesScreen(
    barActions: MutableStateFlow<TopBarUiState>,
    onItemCLicked: (NewsHeadline) -> Unit,
    viewModel: SavedHeadlinesViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    // setup app bar
    barActions.update {
        it.copy(
            title = "Saved Articles",
            actions = emptyList()
        )
    }

    SavedHeadlineList(uiState = uiState){
        onItemCLicked(it)
    }

    remember {
        viewModel.getSavedHeadlines()
    }
}

@Composable
fun SavedHeadlineList(
    uiState: SavedHeadlinesUiState,
    onItemClickListener: (NewsHeadline) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeadLineLazyList(items = uiState.savedHeadlines) {
            onItemClickListener(it)
        }

        //todo: remove hardcoded values
        if(uiState.isLoading) {
            ListRefreshIndicator(Modifier.align(Alignment.TopCenter))
        }

    }
}

@Preview
@Composable
fun SavedHeadLineListPreview(){
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
    SavedHeadlineList(uiState = uiState) {
        
    }
}

