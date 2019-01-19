package com.dasilvacarlos.moviesstand.domain.search

import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.data.generics.ServiceError
import com.dasilvacarlos.moviesstand.data.models.SearchMovieResultModel
import com.dasilvacarlos.moviesstand.data.workers.search.SearchProvider
import com.dasilvacarlos.moviesstand.data.workers.search.SearchReceiver
import com.dasilvacarlos.moviesstand.data.workers.search.SearchWorker
import com.dasilvacarlos.moviesstand.presentation.generic.MovieStandApplication


class SearchInteractor(view: SearchViewLogic): SearchInteractorLogic, SearchReceiver {

    private val searchWorker: SearchProvider = SearchWorker(this)
    private val presenter: SearchPresenterLogic = SearchPresenter(view)

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

    override fun cancelRequests() {
        searchWorker.cancelRequests()
    }

    override fun onReceiveSearchResult(result: SearchMovieResultModel, query: String, page: Int) {
        //TODO favorite logic

        if (lastSearchRequest?.search == query) {
            val response = SearchUserCases.SearchForMovieTitle
                    .Result(searchResult = result,
                            favorites = HashMap())

            presenter.presentSearchResponse(response)
        }
    }

    override fun onSearchError(query: String, page: Int, error: ServiceError) {
        lastSearchRequest?.let { request ->
            if (request.search == query) {
                presenter.presentError(request, error)
            }
        }
    }
}