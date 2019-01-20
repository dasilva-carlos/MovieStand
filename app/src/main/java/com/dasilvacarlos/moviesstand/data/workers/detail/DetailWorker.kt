package com.dasilvacarlos.moviesstand.data.workers.detail

import com.dasilvacarlos.moviesstand.data.generics.ServiceError
import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.data.network.services.OmdbService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailWorker(val receiver: DetailReceiver) : DetailProvider {

    private val callsList: MutableList<Call<MovieModel>> = mutableListOf()

    override fun searchForMovieById(id: String) {
        callsList.add(OmdbService.searchById(id, getCallback()))
    }

    override fun cancelRequests() {
        callsList.forEach { call ->
            call.cancel()
        }
        callsList.clear()
    }

    private fun getCallback(): Callback<MovieModel> = object: Callback<MovieModel> {

        override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
            val body = response.body()
            if (body != null) {
                receiver.onReceiveResult(body)
            } else {
                receiver.onError(ServiceError.resolveError(response))
            }
        }

        override fun onFailure(call: Call<MovieModel>, t: Throwable) {
            receiver.onError(ServiceError.resolveError(t))
        }
    }
}