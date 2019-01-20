package com.dasilvacarlos.moviesstand.data.workers.detail


interface DetailProvider {
    fun searchForMovieById(id: String)
    fun cancelRequests()
}