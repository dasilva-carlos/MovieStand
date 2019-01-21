package com.dasilvacarlos.moviesstand.data.workers.recommendations

import android.content.Context
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.data.generics.ServiceError
import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.data.network.services.OmdbService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecommendationsWorker(val context: Context, val receiver: RecommendationsReceiver): RecommendationsProvider {
    companion object {
        const val MAX_PARALLEL_REQUESTS = 5
    }

    private var quantityToAsk = 0
    private val idsList: MutableList<String> = mutableListOf()
    private val callsList: MutableList<Call<MovieModel>> = mutableListOf()

    override fun subscribeToRecommendations(quantity: Int) {
        quantityToAsk += quantity
        if(idsList.count() < quantityToAsk) {
            val recommendationsList = context.resources.getStringArray(R.array.recommendations)
            idsList.addAll(recommendationsList.map { it.substringBefore("-") })
            idsList.shuffle()
        }

        while (requestNext()){}
    }

    override fun unsubscribe() {
        quantityToAsk = 0
        cancelRequests()
    }

    private fun requestNext(): Boolean {
        if (callsList.size < MAX_PARALLEL_REQUESTS && idsList.count() > 0 && quantityToAsk > 0) {
            quantityToAsk--
            val id = idsList.removeAt(0)
            callsList.add(OmdbService.searchById(id, getCallback()))
            return true
        } else {
            return false
        }
    }

    private fun getCallback(): Callback<MovieModel> = object: Callback<MovieModel> {

        override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
            val body = response.body()
            callsList.remove(call)
            if (body != null) {
                receiver.onRecommendationReceived(body)
                while (requestNext()) {}
            } else {
                receiver.onError(ServiceError.resolveError(response))
                unsubscribe()
            }
        }

        override fun onFailure(call: Call<MovieModel>, t: Throwable) {
            receiver.onError(ServiceError.resolveError(t))
            unsubscribe()
        }
    }

    private fun cancelRequests() {
        callsList.forEach { call ->
            call.cancel()
        }
        callsList.clear()
    }
}