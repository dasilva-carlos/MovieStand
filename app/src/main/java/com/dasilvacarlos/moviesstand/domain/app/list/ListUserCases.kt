package com.dasilvacarlos.moviesstand.domain.app.list

import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.data.workers.favorites.FavoritesProvider
import com.dasilvacarlos.moviesstand.domain.generics.InteractorLogic
import com.dasilvacarlos.moviesstand.domain.generics.PresenterLogic
import com.dasilvacarlos.moviesstand.domain.generics.ViewLogic


class ListUserCases {
    class FavoritesList {
        class Request(val orderBy: FavoritesProvider.OrderByEnum)
        class Result(val favorites: List<MovieModel>,
                     val orderBy: FavoritesProvider.OrderByEnum)
        class ViewModel(val items: List<Item>) {
            class Header(val text: String): Item()
            class Movie(val title: String): Item()
            abstract class Item
        }
    }
}

interface ListInteractorLogic: InteractorLogic {
    fun requestFavorites(request: ListUserCases.FavoritesList.Request)
}

interface ListPresenterLogic: PresenterLogic {
    fun presentFavorites(response: ListUserCases.FavoritesList.Result)
}

interface ListViewLogic: ViewLogic {
    fun displayFavorites(viewModel: ListUserCases.FavoritesList.ViewModel)
}

interface ListDataStore {
    val moviesFavorites: ArrayList<MovieModel>
}