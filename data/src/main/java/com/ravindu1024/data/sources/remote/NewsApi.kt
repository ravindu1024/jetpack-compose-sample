package com.ravindu1024.data.sources.remote

import com.ravindu1024.data.dto.HeadlinesDto
import com.ravindu1024.data.dto.NewsSourcesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v2/top-headlines/sources")
    suspend fun getHeadlineSources(@Query("country") country: String): NewsSourcesDto

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("page") pageNum: Int,
        @Query("pageSize") pageSize: Int,
        @Query("sources") sources: String
    ): HeadlinesDto
}