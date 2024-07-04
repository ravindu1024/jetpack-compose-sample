package com.ravindu1024.data.dto


data class HeadlinesDto(
    val totalResults: Int,
    val articles: List<NewsHeadlineDto>
)

data class NewsHeadlineDto(
    val source: NewsHeadlineSource,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String
)

data class NewsHeadlineSource(
    val id: String?,
    val name: String?
)