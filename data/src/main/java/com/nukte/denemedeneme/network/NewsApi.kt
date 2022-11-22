package com.nukte.denemedeneme.network

import com.nukte.denemedeneme.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    companion object {
        const val BASE_URL = "https://api.newscatcherapi.com/v2/"
    }

    @GET("latest_headlines?countries=TR&when=12h")
    suspend fun getNews(
        @Query("page") page: Int,
    ): NewsResponse

    @GET("search?search_in=title&countries=TRt")
    suspend fun searchNews(
        @Query("q") query: String,
    ): NewsResponse
}