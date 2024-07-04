package com.ravindu1024.domain.model

data class NewsSource(
    val id: String,
    val name: String,
    var isSaved: Boolean = false
)