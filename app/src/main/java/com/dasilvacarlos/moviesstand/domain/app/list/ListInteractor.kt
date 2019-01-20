package com.dasilvacarlos.moviesstand.domain.app.list

import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.data.workers.favorites.FavoritesProvider
import com.dasilvacarlos.moviesstand.data.workers.favorites.FavoritesReceiver
import com.dasilvacarlos.moviesstand.data.workers.favorites.FavoritesWorker


class ListInteractor(view: ListViewLogic) : ListInteractorLogic, FavoritesReceiver, ListDataStore {

    override val moviesFavorites: ArrayList<MovieModel> = arrayListOf()

    private val worker: FavoritesWorker = FavoritesWorker(this)
    private val presenter: ListPresenterLogic = ListPresenter(view)

    override fun requestFavorites(request: ListUserCases.FavoritesList.Request) {
        worker.getFavoritesList(request.orderBy)
    }

    override fun cancelRequests() {}

    override fun onFavoritesList(movies: List<MovieModel>, orderBy: FavoritesProvider.OrderByEnum) {
        moviesFavorites.clear()
        moviesFavorites.addAll(movies.asIterable())
        presenter.presentFavorites(ListUserCases.FavoritesList.Result(movies, orderBy))
    }
}