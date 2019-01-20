package com.dasilvacarlos.moviesstand.domain.app.favorites

import com.dasilvacarlos.moviesstand.data.generics.ServiceError

class FavoritesPresenter(val view: FavoritesViewLogic): FavoritesPresenterLogic {
    override fun presentFavorites(response: FavoritesUserCases.FavoritesList.Result) {
        val items = response.favorites.map {
            FavoritesUserCases.FavoritesList.ViewModel.Item(it.title, it.poster)
        }
        view.displayFavorites(FavoritesUserCases.FavoritesList.ViewModel(items))
    }

    override fun presentError(request: Any, serviceError: ServiceError) {
        view.displayError(request, error = serviceError)
    }
}