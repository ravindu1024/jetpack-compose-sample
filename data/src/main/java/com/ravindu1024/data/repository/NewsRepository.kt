package com.ravindu1024.data.repository

import com.ravindu1024.data.dto.NewsHeadlineDto
import com.ravindu1024.data.dto.NewsSourceDto
import com.ravindu1024.data.entity.HeadlineEntity
import com.ravindu1024.data.entity.SavedSourceEntity
import com.ravindu1024.data.sources.local.HeadlinesDao
import com.ravindu1024.data.sources.remote.NewsApi
import com.ravindu1024.data.sources.local.NewsSourcesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class NewsRepository(
    private val newsApi: NewsApi,
    private val newsSourcesDao: NewsSourcesDao,
    private val headlinesDao: HeadlinesDao
) {

    fun getSavedSources(): Flow<List<String>> {
        return newsSourcesDao.getAllRows()
            .map { rows ->
                return@map rows.map { it.id }
            }
    }

    suspend fun getNewsSources(country: String): Flow<List<NewsSourceDto>> {
        return flow {
            val sources = newsApi.getHeadlineSources(country)
            emit(sources.sources)
        }
    }

    suspend fun getHeadlines(
        pageNum: Int,
        pageSize: Int,
        sources: String
    ): Flow<List<NewsHeadlineDto>> {
        return flow {
            val headlines = newsApi.getTopHeadlines(pageNum, pageSize, sources)
            emit(headlines.articles)
        }
    }

    fun insertSavedSource(source: String) {
        newsSourcesDao.insert(
            SavedSourceEntity(id = source)
        )
    }

    fun deleteSavedSource(source: String) {
        newsSourcesDao.delete(
            SavedSourceEntity(id = source)
        )
    }

    fun addSavedHeadline(headline: HeadlineEntity) {
        headlinesDao.insert(headline)
    }

    fun deleteHeadline(headline: HeadlineEntity) {
        headlinesDao.delete(headline)
    }

    fun getAllSavedHeadlines(): Flow<List<HeadlineEntity>> {
        return headlinesDao.getAll()
    }
}