package com.dasilvacarlos.moviesstand.domain.app.favorites

import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.data.workers.favorites.FavoritesProvider
import com.dasilvacarlos.moviesstand.data.workers.favorites.FavoritesReceiver
import com.dasilvacarlos.moviesstand.data.workers.favorites.FavoritesWorker


class FavoriteInteractor(view: FavoritesViewLogic): FavoritesInteractorLogic, FavoritesReceiver, FavoritesDataStore {

    override val moviesFavorites: ArrayList<MovieModel> = arrayListOf()

    private val worker: FavoritesWorker = FavoritesWorker(this)
    private val presenter: FavoritesPresenterLogic = FavoritesPresenter(view)

    override fun requestFavorites(request: FavoritesUserCases.FavoritesList.Request) {
        worker.getFavoritesList(FavoritesProvider.OrderByEnum.TITLE)
    }

    override fun cancelRequests() {}

    override fun onFavoritesList(movies: List<MovieModel>, orderBy: FavoritesProvider.OrderByEnum) {
        moviesFavorites.clear()
        moviesFavorites.addAll(movies.asIterable())
        presenter.presentFavorites(FavoritesUserCases.FavoritesList.Result(movies))
    }
}