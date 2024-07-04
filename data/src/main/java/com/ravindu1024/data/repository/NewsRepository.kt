package com.ravindu1024.data.repository

import com.ravindu1024.data.dto.NewsHeadlineDto
import com.ravindu1024.data.dto.NewsSourceDto
import com.ravindu1024.data.entity.HeadlineEntity
import com.ravindu1024.data.entity.SavedSourceEntity
import com.ravindu1024.data.sources.HeadlinesDao
import com.ravindu1024.data.sources.NewsApi
import com.ravindu1024.data.sources.NewsSourcesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepository constructor(
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

    suspend fun getNewsSources(country: String): Flow<List<NewsSourceDto>> {
        return flow {
            val sources = newsApi.getHeadlineSources(country)
            emit(sources.sources)
        }
    }

    suspend fun getHeadlines(pageNum: Int, pageSize: Int, sources: String): Flow<List<NewsHeadlineDto>>{
        return flow{
            val headlines = newsApi.getTopHeadlines(pageNum, pageSize, sources)
            emit(headlines.articles)
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

    suspend fun addSavedHeadline(headline: HeadlineEntity){
        headlinesDao.insert(headline)
    }

    suspend fun deleteHeadline(headline: HeadlineEntity){
        headlinesDao.delete(headline)
    }

    suspend fun getAllSavedHeadlines(): Flow<List<HeadlineEntity>>{
        return flow {
            emit(
                headlinesDao.getAll()
            )
        }
    }

}