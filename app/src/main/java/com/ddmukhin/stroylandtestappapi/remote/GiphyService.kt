package com.ddmukhin.stroylandtestappapi.remote

import com.ddmukhin.stroylandtestappapi.di.AppModule
import com.ddmukhin.stroylandtestappapi.di.RemoteModule
import com.ddmukhin.stroylandtestappapi.remote.data.GiphyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {

    @GET("/v1/gifs/search")
    suspend fun search(
        @Query("q") query: String,
        @Query("limit") limit: Int
    ): Response<GiphyResponse>

}