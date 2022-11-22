package com.nukte.denemedeneme.ui.favorite

import androidx.lifecycle.*
import com.nukte.denemedeneme.model.News
import com.nukte.denemedeneme.repository.NewsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val newsRepositoryImp: NewsRepositoryImpl
) : ViewModel() {

    private val newsLiveData = MutableLiveData<List<News>>()
    var news: LiveData<List<News>> = newsLiveData

    fun getSavedNews() = newsRepositoryImp.getAllNews()


    fun deleteNews(news: News) = viewModelScope.launch {
        newsRepositoryImp.deleteNews(news._id)
        news.isSaved=false
    }



}