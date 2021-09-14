package com.ddmukhin.stroylandtestappapi.di

import android.util.Log
import com.ddmukhin.stroylandtestappapi.domain.GiphyConverter
import com.ddmukhin.stroylandtestappapi.remote.GiphyService
import com.ddmukhin.stroylandtestappapi.remote.data.GiphyResponse
import com.ddmukhin.stroylandtestappapi.ui.data.Giphy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {
    companion object {
        const val BASE_URL = "https://api.giphy.com"
        const val API_KEY = "Thj7aK1WdSs8NkAteJIizXDkP98HLZj1"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val url = original.url.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                return@addInterceptor chain.proceed(original.newBuilder().url(url).build())
            }
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) })
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): GiphyService = retrofit.create(GiphyService::class.java)

    @Provides
    @Singleton
    fun provideConverter(): GiphyConverter = object : GiphyConverter {
        override fun fromRemoteToUi(response: Response<GiphyResponse>): List<Giphy>? {
            if (!response.isSuccessful || response.body() == null)
                return null
            return response.body()!!.data.filter {
                it.images.fixed_height.mp4 != null
            }.map {
                Giphy(
                    id = it.id,
                    url = it.url,
                    username = it.username,
                    title = it.title,
                    link = it.images.fixed_height.mp4!!,
                    width = it.images.fixed_height.width.toInt(),
                    height = it.images.fixed_height.height.toInt()
                )
            }.distinctBy { it.id }
        }

    }
}