package com.dasilvacarlos.moviesstand.data.workers.search

import com.dasilvacarlos.moviesstand.data.generics.ServiceError
import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.data.models.OmdbError
import com.dasilvacarlos.moviesstand.data.models.SearchMovieResultModel
import com.dasilvacarlos.moviesstand.data.network.services.OmdbService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchWorker(val receiver: SearchReceiver) : SearchProvider {

    private val callsList: MutableList<Call<SearchMovieResultModel>> = mutableListOf()

    override fun searchForMovieByTitle(title: String, page: Int) {
        val query = title
        callsList.add(OmdbService.searchByName(query, page + 1, getCallback(title, page)))
    }

    override fun cancelRequests() {
        callsList.forEach { call ->
            call.cancel()
        }
        callsList.clear()
    }

    private fun getCallback(title: String, page: Int): Callback<SearchMovieResultModel> = object: Callback<SearchMovieResultModel> {

        override fun onResponse(call: Call<SearchMovieResultModel>, response: Response<SearchMovieResultModel>) {
            val body = response.body()
            if (body != null) {
                receiver.onReceiveSearchResult(body, title, page)
            } else {
                receiver.onSearchError(title, page, ServiceError.resolveError(response))
            }
        }

        override fun onFailure(call: Call<SearchMovieResultModel>, t: Throwable) {
            receiver.onSearchError(title, page, ServiceError.resolveError(t))
        }
    }
}