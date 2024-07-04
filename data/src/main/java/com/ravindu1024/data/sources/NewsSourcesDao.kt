package com.ravindu1024.data.sources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ravindu1024.data.entity.SavedSourceEntity

@Dao
interface NewsSourcesDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(source: SavedSourceEntity)

    @Delete
    suspend fun delete(source: SavedSourceEntity)

    @Query("SELECT * FROM saved_sources")
    suspend fun getAllRows(): List<SavedSourceEntity>
}