package com.ravindu1024.newsbrowser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ravindu1024.newsbrowser.model.domain.NewsHeadline
import com.ravindu1024.newsbrowser.ui.NavItem
import com.ravindu1024.newsbrowser.ui.components.HeadLineLazyList
import com.ravindu1024.newsbrowser.ui.components.ListRefreshIndicator
import com.ravindu1024.newsbrowser.ui.state.HeadlinesUiState
import com.ravindu1024.newsbrowser.ui.state.TopBarUiState
import com.ravindu1024.newsbrowser.ui.theme.CustomTypography
import com.ravindu1024.newsbrowser.ui.theme.Gray100
import com.ravindu1024.newsbrowser.ui.viewmodels.HeadlineViewModel
import com.ravindu1024.newsbrowser.utils.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun HeadLineScreen(
    barActions: MutableStateFlow<TopBarUiState>,
    onItemCLicked: (NewsHeadline) -> Unit,
    viewModel: HeadlineViewModel = hiltViewModel()
){
    // setup app bar
    barActions.update {
        it.copy(
            title = "Headlines",
            actions = emptyList()
        )
    }

    val uiState by viewModel.uiState.collectAsState()

    val pullRefreshState = rememberPullToRefreshState()
    if (pullRefreshState.isRefreshing) {
        viewModel.getHeadlines()
    }

    if(!uiState.isLoading){
        pullRefreshState.endRefresh()
    }

    HeadlineList(
        pullRefreshState = pullRefreshState,
        uiState = uiState,
    ) { selectedHeadline ->
        onItemCLicked(selectedHeadline)
    }

    remember {
        viewModel.getHeadlines()
    }
}


@Composable
fun HeadlineList(
    pullRefreshState: PullToRefreshState,
    uiState: HeadlinesUiState,
    onItemClickListener: (NewsHeadline) -> Unit
){
    Box(
        modifier = Modifier
            .nestedScroll(pullRefreshState.nestedScrollConnection)
            .fillMaxSize()
    ) {
        HeadLineLazyList(items = uiState.headlines) {
            onItemClickListener(it)
        }

        //todo: remove hardcoded values
        if(uiState.isLoading) {
            ListRefreshIndicator(Modifier.align(Alignment.TopCenter))
        }

        PullToRefreshContainer(
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )

    }
}

@Preview
@Composable
fun HeadLineListPreview(){
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
    HeadlineList(pullRefreshState = refreshState, uiState = uiState) {

    }
}