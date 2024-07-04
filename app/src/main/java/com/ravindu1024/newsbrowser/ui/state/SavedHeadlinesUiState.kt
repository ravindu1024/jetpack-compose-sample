package com.ravindu1024.newsbrowser.ui.state

import com.ravindu1024.domain.model.NewsHeadline

data class SavedHeadlinesUiState(
    val isLoading: Boolean = false,
    val savedHeadlines: List<NewsHeadline> = emptyList(),
    val error: Throwable? = null
)