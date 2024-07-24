package com.ravindu1024.data.sources.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ravindu1024.data.entity.HeadlineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HeadlinesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(headline: HeadlineEntity)

    @Delete
    fun delete(headline: HeadlineEntity)

    @Query("SELECT * FROM saved_headlines")
    fun getAll(): Flow<List<HeadlineEntity>>
}