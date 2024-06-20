package com.ravindu1024.newsbrowser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.ravindu1024.newsbrowser.model.domain.NewsSource
import com.ravindu1024.newsbrowser.ui.state.SourcesListUiState
import com.ravindu1024.newsbrowser.ui.viewmodels.SourcesViewModel

@Composable
fun NewsSourcesScreen(
    viewModel: SourcesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()


    val pullRefreshState = rememberPullToRefreshState()
    if (pullRefreshState.isRefreshing) {
        viewModel.getSources()
    }

    if(!uiState.isLoading){
        pullRefreshState.endRefresh()
    }

    SourcesList(
        pullRefreshState = pullRefreshState,
        uiState = uiState,
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
fun SourcesList(
    pullRefreshState: PullToRefreshState,
    uiState: SourcesListUiState,
    onSwitchClicked: (Boolean, NewsSource) -> Unit
){
    Box(modifier = Modifier
        .nestedScroll(pullRefreshState.nestedScrollConnection)
        .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(uiState.sources) { index, source ->
                ListItem(modifier = Modifier
                    .padding(0.dp)
                    .background(Color.Blue), headlineContent = {
                    ConstraintLayout(modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp)) {
                        val (text, switch) = createRefs()
                        Text(
                            text = source.name,
                            modifier = Modifier.constrainAs(text){
                                start.linkTo(parent.start, margin = 0.dp)
                                top.linkTo(parent.top, margin = 0.dp)
                                bottom.linkTo(parent.bottom, margin = 0.dp)
                                end.linkTo(switch.start)
                                width = Dimension.fillToConstraints
                            }
                        )
                        Switch(
                            checked = uiState.savedSources.contains(source.id),
                            onCheckedChange = { onSwitchClicked(it, source) },
                            modifier = Modifier.constrainAs(switch){
                                top.linkTo(parent.top, margin = 0.dp)
                                //bottom.linkTo(parent.bottom, margin = 0.dp)
                                end.linkTo(parent.end, margin = 0.dp)

                            }
                        )
                    }
                })
            }
        }

        //todo: remove hardcoded values
        if(uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(28.dp)
                    .padding(top = 32.dp)
            )
        }

        PullToRefreshContainer(
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview
@Composable
fun SourcesListPreview(){
    val sources = listOf(
        NewsSource(id = "", name = "ABC"),
        NewsSource(id = "", name = "The Guardian"),
        NewsSource(id = "", name = "ljvnskldj skljnvkljsdnv skljvnskljv \nsljnvklsjvn\n slnvsjklv")
    )
    val uiState = SourcesListUiState(sources = sources)
    SourcesList(pullRefreshState = PullToRefreshState(positionalThresholdPx = 0f), uiState = uiState, {_, _ ->})
}
