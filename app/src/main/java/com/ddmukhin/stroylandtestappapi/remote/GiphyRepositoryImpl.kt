package com.ddmukhin.stroylandtestappapi.remote

import android.util.Log
import com.ddmukhin.stroylandtestappapi.domain.GiphyConverter
import com.ddmukhin.stroylandtestappapi.ui.data.Giphy

class GiphyRepositoryImpl(
    private val giphyService: GiphyService,
    private val giphyConverter: GiphyConverter
) : GiphyRepository {
    override suspend fun search(query: String, limit: Int): List<Giphy>? {
        return try {
            giphyConverter.fromRemoteToUi(giphyService.search(query, limit))
        }catch (e: Exception){
            Log.e("Repository", e.stackTraceToString())
            null
        }
    }
}