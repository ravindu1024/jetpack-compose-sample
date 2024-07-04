package com.ravindu1024.domain.usecases

import com.ravindu1024.data.repository.NewsRepository
import com.ravindu1024.domain.model.NewsSource
import com.ravindu1024.domain.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SourcesUseCase constructor(
    private val newsRepository: NewsRepository
) {

    suspend fun getAllLocalSources(): Flow<List<NewsSource>> {
        return newsRepository
            .getNewsSources("au")
            .map { sources ->
                sources.map { it.toDomain() }
            }
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