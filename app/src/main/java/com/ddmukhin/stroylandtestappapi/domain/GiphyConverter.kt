package com.ddmukhin.stroylandtestappapi.domain

import com.ddmukhin.stroylandtestappapi.remote.data.GiphyResponse
import com.ddmukhin.stroylandtestappapi.ui.data.Giphy
import retrofit2.Response

interface GiphyConverter {

    fun fromRemoteToUi(response: Response<GiphyResponse>) : List<Giphy>?

}