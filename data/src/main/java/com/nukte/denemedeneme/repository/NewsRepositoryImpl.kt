package com.nukte.denemedeneme.repository

import androidx.lifecycle.LiveData
import com.nukte.denemedeneme.model.News
import com.nukte.denemedeneme.db.NewsDao
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao
): NewsRepository {
    override suspend fun saveNews(news: News) = newsDao.insert(news)
    override fun getAllNews(): LiveData<List<News>> = newsDao.getAllNews()
    override suspend fun deleteNews(_id: String) {
        newsDao.deleteNews(_id)
    }

}