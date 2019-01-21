package com.dasilvacarlos.moviesstand.data.workers.recommendations

import com.dasilvacarlos.moviesstand.data.generics.ServiceError
import com.dasilvacarlos.moviesstand.data.models.MovieModel


interface RecommendationsReceiver {
    fun onRecommendationReceived(movie: MovieModel)
    fun onError(error: ServiceError)
}