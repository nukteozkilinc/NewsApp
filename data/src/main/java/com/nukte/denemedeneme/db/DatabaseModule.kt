package com.nukte.denemedeneme.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao {
        return newsDatabase.newsDao()
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        @ApplicationContext appContext: Context
    ): NewsDatabase {
        return Room.databaseBuilder(
            appContext,
            NewsDatabase::class.java,
            "news_database"
        ).fallbackToDestructiveMigration().build()
    }

}