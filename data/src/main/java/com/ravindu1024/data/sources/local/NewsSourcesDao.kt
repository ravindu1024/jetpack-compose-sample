package com.ravindu1024.data.sources.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ravindu1024.data.entity.SavedSourceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsSourcesDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(source: SavedSourceEntity)

    @Delete
    fun delete(source: SavedSourceEntity)

    @Query("SELECT * FROM saved_sources")
    fun getAllRows(): Flow<List<SavedSourceEntity>>
}