package com.ravindu1024.newsbrowser.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_headlines")
data class HeadlineEntity(
    @PrimaryKey
    val url: String,

    val source: String?,
    val author: String?,
    val title: String,
    val description: String,
    val urlToImage: String?,
    val publishedAt: String
)