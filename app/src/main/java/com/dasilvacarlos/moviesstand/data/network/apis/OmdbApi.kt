package com.dasilvacarlos.moviesstand.data.network.apis

import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.data.models.SearchMovieResultModel
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query

interface OmdbApi {
    @GET(".")
    fun searchMovieByTitle(@Query("apiKey") apiKey: String,
                           @Query("s") titleQuery: String,
                           @Query("page") page: Int,
                           @Query("plot") plot: String) : Call<SearchMovieResultModel>

    @GET(".")
    fun searchMovieById(@Query("apiKey") apiKey: String,
                        @Query("i") id: String,
                        @Query("plot") plot: String) : Call<MovieModel>
}