package com.nukte.denemedeneme.repository

import androidx.lifecycle.LiveData
import com.nukte.denemedeneme.model.News

interface NewsRepository {
    suspend fun saveNews(news: News)
    fun getAllNews(): LiveData<List<News>>
    suspend fun deleteNews(_id: String)

}