package com.ravindu1024.newsbrowser.domain

import com.ravindu1024.newsbrowser.model.domain.NewsHeadline
import com.ravindu1024.newsbrowser.model.entity.HeadlineEntity
import com.ravindu1024.newsbrowser.utils.DateUtils

fun NewsHeadline.toEntity() = HeadlineEntity(
    url = url,
    source = source,
    title = title,
    description = description,
    urlToImage = urlToImage,
    author = author,
    publishedAt = publishedAt
)

fun HeadlineEntity.toDomain() = NewsHeadline(
    source = source,
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt
)