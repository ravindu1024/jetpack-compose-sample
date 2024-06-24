package com.ravindu1024.newsbrowser.data.repository

import com.ravindu1024.newsbrowser.data.sources.HeadlinesDao
import com.ravindu1024.newsbrowser.data.sources.NewsApi
import com.ravindu1024.newsbrowser.data.sources.NewsSourcesDao
import com.ravindu1024.newsbrowser.domain.toDomain
import com.ravindu1024.newsbrowser.domain.toEntity
import com.ravindu1024.newsbrowser.model.domain.NewsHeadline
import com.ravindu1024.newsbrowser.model.domain.NewsSource
import com.ravindu1024.newsbrowser.model.dto.toDomain
import com.ravindu1024.newsbrowser.model.entity.SavedSourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsSourcesDao: NewsSourcesDao,
    private val headlinesDao: HeadlinesDao
) {

    suspend fun getSavedSources(): Flow<List<String>>{
        return flow {
            val sources = newsSourcesDao.getAllRows().map { it.id }
            emit(sources)
        }
    }

    suspend fun getNewsSources(country: String): Flow<List<NewsSource>> {
        return flow {
            val sources = newsApi.getHeadlineSources(country)
            emit(sources.toDomain())
        }
    }

    suspend fun getHeadlines(pageNum: Int, pageSize: Int, sources: String): Flow<List<NewsHeadline>>{
        return flow{
            val headlines = newsApi.getTopHeadlines(pageNum, pageSize, sources)
            emit(headlines.toDomain())
        }
    }

    suspend fun insertSavedSource(source: String){
        newsSourcesDao.insert(
            SavedSourceEntity(id = source)
        )
    }

    suspend fun deleteSavedSource(source: String){
        newsSourcesDao.delete(
            SavedSourceEntity(id = source)
        )
    }

    suspend fun addSavedHeadline(headline: NewsHeadline){
        headlinesDao.insert(headline.toEntity())
    }

    suspend fun deleteHeadline(headline: NewsHeadline){
        headlinesDao.delete(headline.toEntity())
    }

    suspend fun getAllSavedHeadlines(): Flow<List<NewsHeadline>>{
        return flow {
            emit(
                headlinesDao.getAll()
                    .map { it.toDomain() }
            )
        }
    }

}