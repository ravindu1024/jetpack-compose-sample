package com.ravindu1024.newsbrowser.features.sources

import com.ravindu1024.domain.model.NewsSource

data class SourcesListUiState(
    val sources: List<NewsSource> = emptyList(),
    val savedSources: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null
)