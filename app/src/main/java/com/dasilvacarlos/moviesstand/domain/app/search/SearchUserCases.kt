package com.dasilvacarlos.moviesstand.domain.app.search

import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.data.models.ResumedMovieModel
import com.dasilvacarlos.moviesstand.data.models.SearchMovieResultModel
import com.dasilvacarlos.moviesstand.domain.generics.InteractorLogic
import com.dasilvacarlos.moviesstand.domain.generics.PresenterLogic
import com.dasilvacarlos.moviesstand.domain.generics.ViewLogic


class SearchUserCases {
   class SearchForMovieTitle {
       class Request(val search: String)

       class Result(val searchResult: SearchMovieResultModel,
                    val favorites: Map<String, Boolean>)

       class ViewModel(val items: List<Item>?,
                       val isError: Boolean,
                       val errorMessage: String?) {

           class Item (val title: String?,
                       val year: String?,
                       val bannerUrl: String?,
                       var isFavorite: Boolean)
       }
   }

    class ChangeFavorite {
        class Request(val isFavorite: Boolean, val index: Int)
        class Result(val isSuccess: Boolean, val isFavorite: Boolean, val index: Int)
        class ViewModel(val isSuccess: Boolean, val isFavorite: Boolean, val index: Int)
    }

    class Recommendations {
        class Request
        class Result(val movie: MovieModel)
        class ViewModel(val title: String, val bannerUrl: String?)
    }

    class CheckFavorite {
        class Request
        class Result(val movies: List<ResumedMovieModel>, val favorites: Map<String, Boolean>)
        class ViewModel(val isFavorite: List<Boolean>)
    }
}

interface SearchInteractorLogic: InteractorLogic {
    fun searchForMovieTitle(request: SearchUserCases.SearchForMovieTitle.Request)
    fun requestFavoriteChange(request: SearchUserCases.ChangeFavorite.Request)
    fun requestRecommendations(request: SearchUserCases.Recommendations.Request)
    fun checkFavorites(request: SearchUserCases.CheckFavorite.Request)
}

interface SearchPresenterLogic: PresenterLogic {
    fun presentSearchResponse(response: SearchUserCases.SearchForMovieTitle.Result)
    fun presentFavoriteChange(response: SearchUserCases.ChangeFavorite.Result)
    fun presentRecommendations(response: SearchUserCases.Recommendations.Result)
    fun presentFavorites(response: SearchUserCases.CheckFavorite.Result)
}

interface SearchViewLogic: ViewLogic {
    fun displaySearchResult(viewModel: SearchUserCases.SearchForMovieTitle.ViewModel)
    fun displayFavoriteChange(viewModel: SearchUserCases.ChangeFavorite.ViewModel)
    fun displayRecommendations(viewModel: SearchUserCases.Recommendations.ViewModel)
    fun displayFavorites(viewModel: SearchUserCases.CheckFavorite.ViewModel)
}

interface SearchDataStore {
    val moviesSearched: ArrayList<ResumedMovieModel>
    val recommendations: ArrayList<MovieModel>
}