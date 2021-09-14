package com.ddmukhin.stroylandtestappapi.remote.data

data class GiphyResponse(
    val `data`: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)