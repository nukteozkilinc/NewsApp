package com.nukte.denemedeneme.db

import androidx.room.*
import com.nukte.denemedeneme.model.News


@Database(
    entities = [
        News::class
               ],
    version = 2,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}