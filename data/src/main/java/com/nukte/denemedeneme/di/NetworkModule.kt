package com.nukte.denemedeneme.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    class MyInterceptor : Interceptor{
        @Singleton
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val request = originalRequest.newBuilder()
                .header("x-api-key", "uf4LR2GQ5jf74IkNl6NGOPbK3b62_j68H6vszJfvR6o")
                .build()
            val response = chain.proceed(request)
            return response
        }

    }

    @Provides
    @Singleton
    fun providesHttpInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder().addInterceptor(MyInterceptor()).addInterceptor(httpLoggingInterceptor).build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttp : OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .client(okHttp)
            .baseUrl(com.nukte.denemedeneme.network.NewsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit) : com.nukte.denemedeneme.network.NewsApi =
        retrofit.create(com.nukte.denemedeneme.network.NewsApi::class.java)


}
