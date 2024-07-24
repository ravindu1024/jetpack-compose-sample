package com.ravindu1024.newsbrowser.features.newsdetails

import com.ravindu1024.domain.model.NewsHeadline

data class NewsDetailUiState(
    val isLoading: Boolean = false,
    val headline: NewsHeadline? = null,
    val isFavourite: Boolean = false
)