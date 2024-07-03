package com.ravindu1024.newsbrowser.model.domain

import android.os.Parcelable
import com.ravindu1024.newsbrowser.utils.LocalDateTimeSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
@Parcelize
data class NewsHeadline(
    val source: String?,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String
): Parcelable