package com.dasilvacarlos.moviesstand.domain.app.search

import com.dasilvacarlos.moviesstand.data.models.ResumedMovieModel
import com.dasilvacarlos.moviesstand.data.models.SearchMovieResultModel
import com.dasilvacarlos.moviesstand.domain.generics.InteractorLogic
import com.dasilvacarlos.moviesstand.domain.generics.PresenterLogic
import com.dasilvacarlos.moviesstand.domain.generics.ViewLogic


class SearchUserCases {
   class SearchForMovieTitle {

       class Request(val search: String)

       class Result(val searchResult: SearchMovieResultModel,
                    val favorites: HashMap<String, Boolean>)

       class ViewModel(val items: List<Item>?,
                       val isError: Boolean,
                       val errorMessage: String?) {

           class Item (val title: String?,
                       val year: String?,
                       val bannerUrl: String?,
                       val isFavorite: Boolean?)
       }
   }
}

interface SearchInteractorLogic: InteractorLogic {
    fun searchForMovieTitle(request: SearchUserCases.SearchForMovieTitle.Request)
}

interface SearchPresenterLogic: PresenterLogic {
    fun presentSearchResponse(response: SearchUserCases.SearchForMovieTitle.Result)
}

interface SearchViewLogic: ViewLogic {
    fun displaySearchResult(viewModel: SearchUserCases.SearchForMovieTitle.ViewModel)
}

interface SearchDataStore {
    val moviesSearched: ArrayList<ResumedMovieModel>
}