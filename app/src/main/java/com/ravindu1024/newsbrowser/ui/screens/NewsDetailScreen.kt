package com.ravindu1024.newsbrowser.ui.screens

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.ravindu1024.newsbrowser.model.domain.NewsHeadline
import com.ravindu1024.newsbrowser.ui.state.NewsDetailUiState
import com.ravindu1024.newsbrowser.ui.state.TopBarAction
import com.ravindu1024.newsbrowser.ui.state.TopBarUiState
import com.ravindu1024.newsbrowser.ui.theme.NewsBrowserTheme
import com.ravindu1024.newsbrowser.ui.viewmodels.NewsDetailViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@Composable
fun NewsDetailScreen(
    newsHeadline: NewsHeadline,
    barActions: MutableStateFlow<TopBarUiState>,
    viewModel: NewsDetailViewModel = hiltViewModel()
){
    val uiState: NewsDetailUiState by viewModel.uiState.collectAsState()

    // setup app bar
    barActions.update {
        it.copy(
            title = "",
            actions = listOf(
                TopBarAction(
                    icon = if(uiState.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    onClick = {
                        uiState.headline?.let { viewModel.addRemoveFromFavourites(it, !uiState.isFavourite) }
                    }
                )
            )
        )
    }

    if(uiState.headline != null){
        NewsDetailView(uiState = uiState)
    }else{
        Text(text = "Error loading News article")
    }

    remember {
        viewModel.getNewsHeadline(newsHeadline)
    }
}


@Composable
fun NewsDetailView(uiState: NewsDetailUiState){
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            update = {
                it.loadUrl(uiState.headline?.url ?: "")
            }
        )
    }
}

@Preview
@Composable
fun NewsDetailViewPreview(){
    NewsBrowserTheme {
        NewsDetailView(NewsDetailUiState())
    }
}