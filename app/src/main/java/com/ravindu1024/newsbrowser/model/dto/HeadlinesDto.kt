package com.ravindu1024.newsbrowser.model.dto

import com.google.gson.annotations.SerializedName
import com.ravindu1024.newsbrowser.model.domain.NewsHeadline
import com.ravindu1024.newsbrowser.model.domain.NewsSource
import com.ravindu1024.newsbrowser.utils.DateUtils
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

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

fun HeadlinesDto.toDomain() : List<NewsHeadline>{
    return articles.map {
        NewsHeadline(
            source = it.source.name,
            author = it.author,
            title = it.title,
            description = it.description ?: "",
            url = it.url,
            urlToImage = it.urlToImage,
            publishedAt = it.publishedAt
        )
    }
}