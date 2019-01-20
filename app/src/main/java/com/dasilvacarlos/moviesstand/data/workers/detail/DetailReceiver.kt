package com.dasilvacarlos.moviesstand.data.workers.detail

import com.dasilvacarlos.moviesstand.data.generics.ServiceError
import com.dasilvacarlos.moviesstand.data.models.MovieModel


interface DetailReceiver {
    fun onReceiveResult(result: MovieModel)
    fun onError(error: ServiceError)
}