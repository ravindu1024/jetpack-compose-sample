package com.ravindu1024.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ravindu1024.data.sources.HeadlinesDao
import com.ravindu1024.data.sources.NewsSourcesDao
import com.ravindu1024.data.entity.HeadlineEntity
import com.ravindu1024.data.entity.SavedSourceEntity

@Database(entities = [SavedSourceEntity::class, HeadlineEntity::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDao(): NewsSourcesDao

    abstract fun headlineDao(): HeadlinesDao

}

object NewsDatabaseBuilder{
    fun build(context: Context): NewsDatabase{
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "News"
        ).build()
    }
}