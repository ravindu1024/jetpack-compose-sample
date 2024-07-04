package com.ravindu1024.newsbrowser.ui.state

import com.ravindu1024.domain.model.NewsHeadline

data class NewsDetailUiState(
    val isLoading: Boolean = false,
    val headline: NewsHeadline? = null,
    val isFavourite: Boolean = false
)