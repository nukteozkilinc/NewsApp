package com.nukte.denemedeneme.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nukte.denemedeneme.model.News
import com.nukte.denemedeneme.data.news.NewsDataSource
import com.nukte.denemedeneme.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsDataSource: NewsDataSource,
    private val newsRepositoryImp: NewsRepository
) : ViewModel() {
    private val newsLiveData = MutableLiveData<PagingData<News>>()
    val newsFlow : Flow<PagingData<News>> = newsDataSource.getHomeNews().cachedIn(viewModelScope)
     //val news: LiveData<PagingData<News>> = newsLiveData

   /* init {
        fetchNews()
    }*/

    /*fun fetchNews() = viewModelScope.launch{
        newsDataSource.getHomeNews().cachedIn(viewModelScope).collect{
            newsLiveData.value = it
        }
    }*/

    fun saveNews(news: News) = viewModelScope.launch {
        news.isSaved = true
        newsRepositoryImp.saveNews(news)
    }

    fun deleteNews(news: News) = viewModelScope.launch {
        news.isSaved = false
        newsRepositoryImp.deleteNews(news._id)
    }
}