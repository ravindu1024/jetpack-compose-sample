package com.ravindu1024.newsbrowser.model.dto

import com.ravindu1024.newsbrowser.model.domain.NewsSource

data class NewsSourcesDto(
    val sources: List<NewsSourceDto>
)

data class NewsSourceDto(
    val id: String,
    val name: String
)

fun NewsSourcesDto.toDomain(): List<NewsSource> {
    return sources.map {
        NewsSource(
            id = it.id,
            name = it.name
        )
    }
}