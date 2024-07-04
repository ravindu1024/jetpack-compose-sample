package com.ravindu1024.domain.usecases

import com.ravindu1024.domain.model.NewsHeadline
import com.ravindu1024.data.repository.NewsRepository
import com.ravindu1024.domain.toDomain
import com.ravindu1024.domain.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SavedHeadlinesUseCase constructor(
    private val newsRepository: NewsRepository
) {

    suspend fun addHeadline(headline: NewsHeadline): Flow<List<NewsHeadline>> {
        newsRepository.addSavedHeadline(headline.toEntity())

        return newsRepository
            .getAllSavedHeadlines()
            .map { headlines ->
                headlines.map { it.toDomain() }
            }
    }

    suspend fun deleteSavedHeadline(headline: NewsHeadline): Flow<List<NewsHeadline>> {
        newsRepository.deleteHeadline(headline.toEntity())

        return newsRepository
            .getAllSavedHeadlines()
            .map { headlines ->
                headlines.map { it.toDomain() }
            }
    }

    suspend fun getAllSavedHeadlines(): Flow<List<NewsHeadline>> {
        return newsRepository
            .getAllSavedHeadlines()
            .map { headlines ->
                headlines.map { it.toDomain() }
            }
    }
}