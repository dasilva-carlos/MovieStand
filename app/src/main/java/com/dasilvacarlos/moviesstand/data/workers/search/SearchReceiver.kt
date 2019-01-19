package com.dasilvacarlos.moviesstand.data.workers.search

import com.dasilvacarlos.moviesstand.data.generics.ServiceError
import com.dasilvacarlos.moviesstand.data.models.SearchMovieResultModel

interface SearchReceiver {
    fun onReceiveSearchResult(result: SearchMovieResultModel, query: String, page: Int)
    fun onSearchError(query: String, page: Int, error: ServiceError)
}