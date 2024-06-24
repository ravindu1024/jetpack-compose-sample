package com.ravindu1024.newsbrowser.domain

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

    suspend fun getAllSavedSources(): Flow<List<String>> {
        return newsRepository.getSavedSources()
    }

    suspend fun addSavedSource(id: String): Flow<List<String>>{
        newsRepository.insertSavedSource(id)

        return newsRepository.getSavedSources()
    }

    suspend fun deleteSavedSource(id: String): Flow<List<String>>{
        newsRepository.deleteSavedSource(id)

        return newsRepository.getSavedSources()
    }
}