package com.nukte.denemedeneme.di

import com.nukte.denemedeneme.data.news.NewsDataSource
import com.nukte.denemedeneme.data.news.NewsDataSourceImpl
import com.nukte.denemedeneme.repository.NewsRepository
import com.nukte.denemedeneme.repository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindNewsDataSource(newsDataSourceImpl: NewsDataSourceImpl): NewsDataSource

    @Binds
    @Singleton
    abstract fun bindNewsRepository(newsRepositoryImpl: NewsRepositoryImpl) : NewsRepository
}