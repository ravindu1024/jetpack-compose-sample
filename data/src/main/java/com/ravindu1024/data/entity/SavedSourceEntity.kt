package com.ravindu1024.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_sources")
data class SavedSourceEntity(

    @PrimaryKey
    val id: String
)