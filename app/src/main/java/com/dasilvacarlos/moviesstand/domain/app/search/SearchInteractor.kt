package com.dasilvacarlos.moviesstand.domain.app.search

import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.data.generics.ServiceError
import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.data.models.ResumedMovieModel
import com.dasilvacarlos.moviesstand.data.models.SearchMovieResultModel
import com.dasilvacarlos.moviesstand.data.workers.detail.DetailReceiver
import com.dasilvacarlos.moviesstand.data.workers.detail.DetailWorker
import com.dasilvacarlos.moviesstand.data.workers.favorites.FavoritesReceiver
import com.dasilvacarlos.moviesstand.data.workers.favorites.FavoritesWorker
import com.dasilvacarlos.moviesstand.data.workers.search.SearchProvider
import com.dasilvacarlos.moviesstand.data.workers.search.SearchReceiver
import com.dasilvacarlos.moviesstand.data.workers.search.SearchWorker
import com.dasilvacarlos.moviesstand.presentation.generic.MovieStandApplication


class SearchInteractor(view: SearchViewLogic): SearchInteractorLogic, SearchDataStore, SearchReceiver {

    override var moviesSearched: ArrayList<ResumedMovieModel> = arrayListOf()

    private val searchWorker: SearchProvider = SearchWorker(this)
    private val presenter: SearchPresenterLogic = SearchPresenter(view)
    private var detailWorker: DetailWorker? = null

    private var lastSearchRequest: SearchUserCases.SearchForMovieTitle.Request? = null

    override fun searchForMovieTitle(request: SearchUserCases.SearchForMovieTitle.Request) {
        if (request.search.count() >= 3) {
            lastSearchRequest = request
            searchWorker.cancelRequests()
            searchWorker.searchForMovieByTitle(request.search)
        } else {
            var result = SearchMovieResultModel()
            result.response = false
            result.error = MovieStandApplication.instance.applicationContext.getString(R.string.search_keep_type)
            val response = SearchUserCases.SearchForMovieTitle
                    .Result(searchResult = result,
                            favorites = HashMap())

            presenter.presentSearchResponse(response)
        }
    }

    override fun requestFavoriteChange(request: SearchUserCases.ChangeFavorite.Request) {
        val favoritesWorker = FavoritesWorker(getOnSaveStateChangedReceiver(request.index))
        if(request.isFavorite) {
            detailWorker = DetailWorker(getDetailReceiver(request.index))
            detailWorker?.searchForMovieById(moviesSearched[request.index].id ?: "")
        } else {
            favoritesWorker.removeFavorite(moviesSearched[request.index].id ?: "")
        }
    }

    override fun cancelRequests() {
        searchWorker.cancelRequests()
        detailWorker?.cancelRequests()
    }

    override fun onReceiveSearchResult(result: SearchMovieResultModel, query: String, page: Int) {
        val favoritesWorker = FavoritesWorker(getCheckIsFavoriteReceiver(query, result))
        val ids = result.search?.map { it.id ?: "" }
        favoritesWorker.checkIfIsFavorite(ids ?: listOf())
    }

    override fun onSearchError(query: String, page: Int, error: ServiceError) {
        lastSearchRequest?.let { request ->
            if (request.search == query) {
                presenter.presentError(request, error)
            }
        }
    }

    private fun getOnSaveStateChangedReceiver(index: Int)  = object : FavoritesReceiver {
        override fun onSaveResult(isSaved: Boolean, id: String?) {
            presenter.presentFavoriteChange(SearchUserCases.ChangeFavorite.Result(isSaved, isSaved, index))
        }

        override fun onRemoveResult(isRemoved: Boolean, id: String?) {
            presenter.presentFavoriteChange(SearchUserCases.ChangeFavorite.Result(isRemoved, !isRemoved, index))
        }
    }

    private fun getCheckIsFavoriteReceiver(query: String, result: SearchMovieResultModel) = object : FavoritesReceiver {
        override fun onFavoritesCheckResult(isFavorite: Map<String, Boolean>) {
            if (lastSearchRequest?.search == query) {
                val response = SearchUserCases.SearchForMovieTitle
                        .Result(searchResult = result,
                                favorites = isFavorite)
                response.searchResult.search?.let {
                    moviesSearched.clear()
                    moviesSearched.addAll(it)
                }
                presenter.presentSearchResponse(response)
            }
        }
    }

    private fun getDetailReceiver(index: Int) = object : DetailReceiver {
        override fun onError(error: ServiceError) {
            presenter.presentFavoriteChange(SearchUserCases.ChangeFavorite
                    .Result(isSuccess = false, isFavorite =  false, index = index))
        }

        override fun onReceiveResult(result: MovieModel) {
            val favoritesWorker = FavoritesWorker(getOnSaveStateChangedReceiver(index))
            favoritesWorker.saveAsFavorite(result)
        }
    }
}