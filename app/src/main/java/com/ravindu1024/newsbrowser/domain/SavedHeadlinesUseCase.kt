package com.ravindu1024.newsbrowser.domain

import com.ravindu1024.newsbrowser.data.repository.NewsRepository
import com.ravindu1024.newsbrowser.model.domain.NewsHeadline
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavedHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend fun addHeadline(headline: NewsHeadline): Flow<List<NewsHeadline>> {
        newsRepository.addSavedHeadline(headline)
        return newsRepository.getAllSavedHeadlines()
    }

    suspend fun deleteSavedHeadline(headline: NewsHeadline): Flow<List<NewsHeadline>> {
        newsRepository.deleteHeadline(headline)
        return newsRepository.getAllSavedHeadlines()
    }

    suspend fun getAllSavedHeadlines(): Flow<List<NewsHeadline>>{
        return newsRepository.getAllSavedHeadlines()
    }
}