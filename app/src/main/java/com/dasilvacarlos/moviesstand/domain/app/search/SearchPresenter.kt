package com.dasilvacarlos.moviesstand.domain.app.search

import com.dasilvacarlos.moviesstand.data.generics.ServiceError


class SearchPresenter(val view: SearchViewLogic): SearchPresenterLogic {


    override fun presentSearchResponse(response: SearchUserCases.SearchForMovieTitle.Result) {
        val items = response.searchResult.search?.map {
            val isFavorite = response.favorites[it.id] == true
            return@map SearchUserCases.SearchForMovieTitle
                    .ViewModel.Item(it.title,
                                    it.year,
                                    it.poster,
                                    isFavorite)
        }

        val isError = response.searchResult.response != true
        val viewModel = SearchUserCases.SearchForMovieTitle
                .ViewModel( items,
                            isError,
                            response.searchResult.error)
        view.displaySearchResult(viewModel)
    }

    override fun presentFavoriteChange(response: SearchUserCases.ChangeFavorite.Result) {
        view.displayFavoriteChange(
                viewModel = SearchUserCases.ChangeFavorite
                        .ViewModel(response.isSuccess, response.isFavorite, response.index))
    }

    override fun presentRecommendations(response: SearchUserCases.Recommendations.Result) {
        view.displayRecommendations(SearchUserCases.Recommendations
                .ViewModel(response.movie.title ?: "-", response.movie.poster ?: ""))
    }

    override fun presentFavorites(response: SearchUserCases.CheckFavorite.Result) {
        val isFavoriteList = response.movies.map { response.favorites[it.id] == true}
        view.displayFavorites(SearchUserCases.CheckFavorite.ViewModel(isFavoriteList))
    }

    override fun presentError(request: Any, serviceError: ServiceError) {
        view.displayError(request, error = serviceError)
    }
}