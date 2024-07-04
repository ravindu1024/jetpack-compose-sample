package com.ravindu1024.domain

import com.ravindu1024.data.dto.NewsHeadlineDto
import com.ravindu1024.data.entity.HeadlineEntity
import com.ravindu1024.domain.model.NewsHeadline


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

fun NewsHeadlineDto.toDomain() = NewsHeadline(
    source = source.name,
    author = author,
    title = title,
    description = description ?: "",
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt
)