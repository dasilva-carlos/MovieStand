package com.dasilvacarlos.moviesstand.data.workers.favorites

import com.dasilvacarlos.moviesstand.data.models.MovieModel


interface FavoritesProvider {
    fun saveAsFavorite(movie: MovieModel)
    fun removeFavorite(movieId: String)
    fun checkIfIsFavorite(ids: List<String>)
    fun getFavoritesList(orderBy: OrderByEnum = OrderByEnum.RANDOM)

    enum class OrderByEnum {
        TITLE,
        RATING,
        RELEASE,
        RANDOM
    }
}