package com.ravindu1024.newsbrowser.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ravindu1024.newsbrowser.data.sources.HeadlinesDao
import com.ravindu1024.newsbrowser.data.sources.NewsSourcesDao
import com.ravindu1024.newsbrowser.model.entity.HeadlineEntity
import com.ravindu1024.newsbrowser.model.entity.SavedSourceEntity

@Database(entities = [SavedSourceEntity::class, HeadlineEntity::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDao(): NewsSourcesDao

    abstract fun headlineDao(): HeadlinesDao


}