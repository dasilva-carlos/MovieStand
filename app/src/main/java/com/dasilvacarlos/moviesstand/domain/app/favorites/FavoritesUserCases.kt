package com.dasilvacarlos.moviesstand.domain.app.favorites

import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.domain.generics.InteractorLogic
import com.dasilvacarlos.moviesstand.domain.generics.PresenterLogic
import com.dasilvacarlos.moviesstand.domain.generics.ViewLogic


class FavoritesUserCases {
    class FavoritesList {
        class Request
        class Result(val favorites: List<MovieModel>)
        class ViewModel(val items: List<Item>) {
            class Item (val title: String?,
                        val bannerUrl: String?)
        }
    }
}

interface FavoritesInteractorLogic: InteractorLogic {
    fun requestFavorites(request: FavoritesUserCases.FavoritesList.Request)
}

interface FavoritesPresenterLogic: PresenterLogic {
    fun presentFavorites(response: FavoritesUserCases.FavoritesList.Result)
}

interface FavoritesViewLogic: ViewLogic {
    fun displayFavorites(viewModel: FavoritesUserCases.FavoritesList.ViewModel)
}

interface FavoritesDataStore {
    val moviesFavorites: ArrayList<MovieModel>
}