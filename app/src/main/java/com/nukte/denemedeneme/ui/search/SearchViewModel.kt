package com.nukte.denemedeneme.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nukte.denemedeneme.model.News
import com.nukte.denemedeneme.data.news.NewsDataSource
import com.nukte.denemedeneme.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsDataSource: NewsDataSource,
    private val newsRepositoryImp: NewsRepository
) : ViewModel() {
    private val newsLiveData = MutableLiveData<List<News>>()
    val news: LiveData<List<News>> = newsLiveData

    private val _queryMutableLiveData = MutableStateFlow("")
    val query: Flow<String> = _queryMutableLiveData

    init {
        initSearch()
    }

    private fun initSearch() = viewModelScope.launch {
        query.filterNotNull()
            .filter { it.isNotEmpty() }
            .distinctUntilChanged { old, new -> false }
            .onEach { query ->
                val news = newsDataSource.searchHomeNews(query)
                newsLiveData.value = news
            }.launchIn(viewModelScope)
    }


    fun getSearchNews(query: String) = viewModelScope.launch {
        _queryMutableLiveData.value = query
    }

    fun saveNews(news: News) = viewModelScope.launch {
        news.isSaved = true
        newsRepositoryImp.saveNews(news)
    }

    fun deleteNews(news: News) = viewModelScope.launch {
        news.isSaved = false
        newsRepositoryImp.deleteNews(news._id)
    }

    fun refreshSearchNews() = viewModelScope.launch {
        getSearchNews(query.first())
    }


}