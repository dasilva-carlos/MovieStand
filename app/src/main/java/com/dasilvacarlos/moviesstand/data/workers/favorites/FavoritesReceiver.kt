package com.dasilvacarlos.moviesstand.data.workers.favorites

import com.dasilvacarlos.moviesstand.data.models.MovieModel

interface FavoritesReceiver {
    fun onSaveResult(isSaved: Boolean, id: String?) {}
    fun onRemoveResult(isRemoved: Boolean, id: String?) {}
    fun onFavoritesCheckResult(isFavorite: Map<String, Boolean>) {}
    fun onFavoritesList(movies: List<MovieModel>, orderBy: FavoritesProvider.OrderByEnum) {}
}