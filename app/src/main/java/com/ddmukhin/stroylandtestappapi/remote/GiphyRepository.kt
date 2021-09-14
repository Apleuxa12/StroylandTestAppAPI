package com.ddmukhin.stroylandtestappapi.remote

import com.ddmukhin.stroylandtestappapi.ui.data.Giphy

interface GiphyRepository {

    suspend fun search(query: String, limit: Int = 25): List<Giphy>?

}