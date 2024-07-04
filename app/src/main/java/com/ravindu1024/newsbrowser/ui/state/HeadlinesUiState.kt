package com.ravindu1024.newsbrowser.ui.state

import com.ravindu1024.domain.model.NewsHeadline

data class HeadlinesUiState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val headlines: List<NewsHeadline> = emptyList(),
    val pageNum: Int = 0,
    val pageSize: Int = 20
)