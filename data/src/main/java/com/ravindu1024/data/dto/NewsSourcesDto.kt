package com.ravindu1024.data.dto


data class NewsSourcesDto(
    val sources: List<NewsSourceDto>
)

data class NewsSourceDto(
    val id: String,
    val name: String
)