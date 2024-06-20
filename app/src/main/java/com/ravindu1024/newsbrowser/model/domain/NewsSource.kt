package com.ravindu1024.newsbrowser.model.domain

data class NewsSource(
    val id: String,
    val name: String,
    var isSaved: Boolean = false
)