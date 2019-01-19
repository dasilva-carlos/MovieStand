package com.dasilvacarlos.moviesstand.data.network.services

import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.data.models.SearchMovieResultModel
import com.dasilvacarlos.moviesstand.data.network.apis.OmdbApi
import com.dasilvacarlos.moviesstand.data.network.manager.RetrofitManager
import retrofit2.Call
import retrofit2.Callback


object OmdbService {
    private enum class PlotTypes(val code: String) {
        SHORT("short"),
        FULL("full")
    }

    val API_KEY = "514d9820"
    val OmdbService: OmdbApi by lazy {
        RetrofitManager.createService(OmdbApi::class.java)
    }

    fun searchById(id: String, callback: Callback<MovieModel>): Call<MovieModel> =
            OmdbService.searchMovieById(API_KEY, id, PlotTypes.FULL.code).apply {
                enqueue(callback)
            }

    fun searchByName(titleQuery: String, page: Int, callback: Callback<SearchMovieResultModel>): Call<SearchMovieResultModel> =
            OmdbService.searchMovieByTitle(API_KEY, titleQuery, page, PlotTypes.FULL.code).apply {
                enqueue(callback)
            }
}