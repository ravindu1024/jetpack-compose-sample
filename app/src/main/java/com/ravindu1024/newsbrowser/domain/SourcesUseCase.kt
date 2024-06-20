package com.ravindu1024.newsbrowser.domain

import androidx.compose.ui.text.intl.Locale
import com.ravindu1024.newsbrowser.data.repository.NewsRepository
import com.ravindu1024.newsbrowser.model.domain.NewsSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SourcesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend fun getAllLocalSources(): Flow<List<NewsSource>> {
        return newsRepository.getNewsSources("au")
    }
}