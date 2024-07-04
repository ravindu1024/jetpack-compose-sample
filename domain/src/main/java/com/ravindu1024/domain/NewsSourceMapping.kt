package com.ravindu1024.domain

import com.ravindu1024.data.dto.NewsSourceDto
import com.ravindu1024.domain.model.NewsSource

fun NewsSourceDto.toDomain() = NewsSource(
    id = id,
    name = name
)