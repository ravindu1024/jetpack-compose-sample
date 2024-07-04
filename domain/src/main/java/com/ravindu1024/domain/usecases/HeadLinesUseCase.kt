package com.ravindu1024.domain.usecases

import com.ravindu1024.data.repository.NewsRepository
import com.ravindu1024.domain.model.NewsHeadline
import com.ravindu1024.domain.toDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class HeadLinesUseCase constructor(
    private val newsRepository: NewsRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getHeadlines(pageNum: Int, pageSize: Int): Flow<List<NewsHeadline>>{
        return newsRepository.getSavedSources()
            .flatMapMerge { sources ->
                if(sources.isEmpty()){
                    return@flatMapMerge flow {
                        emit(emptyList())
                    }
                }else {
                    newsRepository.getHeadlines(
                        pageSize = pageSize,
                        pageNum = pageNum,
                        sources = sources.joinToString(",")
                    ).map { headlines ->
                        headlines.map { it.toDomain() }
                    }
                }
            }
    }
}