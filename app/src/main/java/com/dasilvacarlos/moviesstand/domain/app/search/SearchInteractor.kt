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
import com.dasilvacarlos.moviesstand.data.workers.recommendations.RecommendationsProvider
import com.dasilvacarlos.moviesstand.data.workers.recommendations.RecommendationsReceiver
import com.dasilvacarlos.moviesstand.data.workers.recommendations.RecommendationsWorker
import com.dasilvacarlos.moviesstand.data.workers.search.SearchProvider
import com.dasilvacarlos.moviesstand.data.workers.search.SearchReceiver
import com.dasilvacarlos.moviesstand.data.workers.search.SearchWorker
import com.dasilvacarlos.moviesstand.presentation.generic.MovieStandApplication


class SearchInteractor(view: SearchViewLogic): SearchInteractorLogic, SearchDataStore, SearchReceiver {

    companion object {
        const val DEFAULT_RECOMMENDATIONS_QUANTITY = 15
    }

    override val recommendations: ArrayList<MovieModel> = arrayListOf()

    override var moviesSearched: ArrayList<ResumedMovieModel> = arrayListOf()

    private val searchWorker: SearchProvider = SearchWorker(this)
    private val presenter: SearchPresenterLogic = SearchPresenter(view)
    private var detailWorker: DetailWorker? = null
    private var recommendationsWorker: RecommendationsProvider =
            RecommendationsWorker(MovieStandApplication.instance.applicationContext, getRecommendationsReceiver())

    private var lastSearchRequest: SearchUserCases.SearchForMovieTitle.Request? = null

    override fun searchForMovieTitle(request: SearchUserCases.SearchForMovieTitle.Request) {
        if (request.search.count() >= 3) {
            lastSearchRequest = request
            searchWorker.cancelRequests()
            searchWorker.searchForMovieByTitle(request.search)
        } else {
            val result = SearchMovieResultModel()
            result.response = false
            result.error = if(request.search.count() > 0){
                MovieStandApplication.instance.applicationContext.getString(R.string.search_keep_type)
            } else {
                MovieStandApplication.instance.applicationContext.getString(R.string.search_type)
            }

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

    override fun requestRecommendations(request: SearchUserCases.Recommendations.Request) {
        recommendationsWorker.subscribeToRecommendations(DEFAULT_RECOMMENDATIONS_QUANTITY)
    }

    override fun checkFavorites(request: SearchUserCases.CheckFavorite.Request) {
        val favoritesWorker = FavoritesWorker(getCheckIsFavoriteReceiver())
        val ids = moviesSearched.map { it.id ?: "" }
        favoritesWorker.checkIfIsFavorite(ids)
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
                result.search?.let {
                    moviesSearched.clear()
                    moviesSearched.addAll(it)
                }

                val response = SearchUserCases.SearchForMovieTitle
                        .Result(searchResult = result,
                                favorites = isFavorite)

                presenter.presentSearchResponse(response)
            }
        }
    }

    private fun getCheckIsFavoriteReceiver() = object : FavoritesReceiver {
        override fun onFavoritesCheckResult(isFavorite: Map<String, Boolean>) {
            presenter.presentFavorites(SearchUserCases.CheckFavorite.Result(moviesSearched, isFavorite))
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

    private fun getRecommendationsReceiver() = object : RecommendationsReceiver{
        override fun onRecommendationReceived(movie: MovieModel) {
            recommendations.add(movie)
            presenter.presentRecommendations(SearchUserCases.Recommendations.Result(movie = movie))
        }

        override fun onError(error: ServiceError) {
            presenter.presentError(SearchUserCases.Recommendations.Request(), error)
        }
    }
}