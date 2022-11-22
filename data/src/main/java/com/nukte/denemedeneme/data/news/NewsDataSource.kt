package com.nukte.denemedeneme.data.news

import androidx.paging.PagingData
import com.nukte.denemedeneme.model.News
import kotlinx.coroutines.flow.Flow

interface NewsDataSource {
     fun getHomeNews() : Flow<PagingData<News>>
     suspend fun searchHomeNews(query : String) : List<News>
}