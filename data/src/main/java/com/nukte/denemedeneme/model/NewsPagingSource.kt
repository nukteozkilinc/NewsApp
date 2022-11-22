package com.nukte.denemedeneme.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nukte.denemedeneme.db.NewsDao
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource(
    private val api: com.nukte.denemedeneme.network.NewsApi,
    private val newsDao: NewsDao
) : PagingSource<Int, News>() {
    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return 1
    /*state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }*/
    }

    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, News> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response = api.getNews(
                page = pageIndex
            )
            val news = response.articles
            news.map {
                newsDao.isSavedBefore(id = it._id).let { isSavedBefore ->
                    when (isSavedBefore) {
                        true -> it.isSaved = true
                        else -> it.isSaved = false
                    }
                }
            }
            val nextKey =
                if (news.isEmpty()) {
                    1
                } else {
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            PagingSource.LoadResult.Page(
                data = news,
                prevKey = null,
                //if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        var TMDB_STARTING_PAGE_INDEX = 1
        var NETWORK_PAGE_SIZE = 50
    }
}