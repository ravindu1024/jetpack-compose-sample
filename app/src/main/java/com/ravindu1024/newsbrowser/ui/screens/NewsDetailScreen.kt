package com.ravindu1024.newsbrowser.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.ravindu1024.newsbrowser.model.domain.NewsHeadline
import com.ravindu1024.newsbrowser.ui.components.WebView
import com.ravindu1024.newsbrowser.ui.state.NewsDetailUiState
import com.ravindu1024.newsbrowser.ui.state.TopBarAction
import com.ravindu1024.newsbrowser.ui.theme.NewsBrowserTheme
import com.ravindu1024.newsbrowser.ui.viewmodels.NewsDetailViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun NewsDetailScreen(
    newsHeadline: NewsHeadline,
    barActions: (String, List<TopBarAction>) -> Unit,
    viewModel: NewsDetailViewModel = hiltViewModel()
) {
    val uiState: NewsDetailUiState by viewModel.uiState.collectAsState()

    // setup app bar
    barActions(
        "",
        listOf(
            TopBarAction(
                icon = if (uiState.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                onClick = {
                    uiState.headline?.let {
                        viewModel.addRemoveFromFavourites(
                            it,
                            !uiState.isFavourite
                        )
                    }
                }
            )
        )
    )

    NewsDetailContent(uiState = uiState)

    remember {
        viewModel.getNewsHeadline(newsHeadline)
        return@remember 0
    }
}


@Composable
fun NewsDetailContent(uiState: NewsDetailUiState) {
    if (uiState.headline != null) {
        WebView(modifier = Modifier.fillMaxWidth(), url = uiState.headline.url)
    } else {
        Text(text = "Error loading News article")
    }
}

@Preview
@Composable
fun NewsDetailContentPreview() {
    val headline = NewsHeadline(
        source = "ABC News",
        author = "John Doe",
        title = "Hello world 2",
        description = "skldvsdkjv sakljvksdjvn \nasdkljvn",
        urlToImage = null,
        url = "cdd",
        publishedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
    )

    NewsBrowserTheme {
        NewsDetailContent(
            uiState = NewsDetailUiState(headline = headline, isFavourite = true)
        )
    }
}