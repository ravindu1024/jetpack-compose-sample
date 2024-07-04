package com.ravindu1024.domain

import com.ravindu1024.domain.model.NewsSource
import com.ravindu1024.data.dto.NewsSourceDto

fun NewsSourceDto.toDomain() = NewsSource(
    id = id,
    name = name
)