package com.ravindu1024.newsbrowser.data.repository

import com.ravindu1024.newsbrowser.data.sources.NewsApi
import com.ravindu1024.newsbrowser.model.domain.NewsSource
import com.ravindu1024.newsbrowser.model.dto.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {

    suspend fun getNewsSources(country: String): Flow<List<NewsSource>> {
        return flow {
            val sources = newsApi.getHeadlineSources(country)
            emit(sources.toDomain())
        }
    }
}