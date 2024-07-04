package com.ravindu1024.data.sources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ravindu1024.data.entity.HeadlineEntity

@Dao
interface HeadlinesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(headline: HeadlineEntity)

    @Delete
    suspend fun delete(headline: HeadlineEntity)

    @Query("SELECT * FROM saved_headlines")
    suspend fun getAll(): List<HeadlineEntity>
}