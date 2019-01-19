package com.dasilvacarlos.moviesstand.data.workers.search


interface SearchProvider {
    fun searchForMovieByTitle(title: String, page: Int = 0)
    fun cancelRequests()
}