package com.ravindu1024.newsbrowser.features.headlines

import com.ravindu1024.domain.model.NewsHeadline

data class HeadlinesUiState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val headlines: List<NewsHeadline> = emptyList(),
    val pageNum: Int = 1,
    val pageSize: Int = 10
)