package com.ravindu1024.newsbrowser.domain

import com.ravindu1024.newsbrowser.data.repository.NewsRepository
import com.ravindu1024.newsbrowser.model.domain.NewsHeadline
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HeadLinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

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
                    )
                }
            }
    }
}