package com.dasilvacarlos.moviesstand.domain.app.detail

import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.domain.generics.InteractorLogic
import com.dasilvacarlos.moviesstand.domain.generics.PresenterLogic
import com.dasilvacarlos.moviesstand.domain.generics.ViewLogic
import java.io.Serializable


class DetailUserCases {
    class DetailTitle {
        class Request
        class Result(val title: String?,
                     val banner: String?,
                     val isFavorite: Boolean)
        class ViewModel(val title: String,
                        val banner: String,
                        val isFavorite: Boolean)
    }

    class DetailInformation {
        class Request
        class Result(val movie: MovieModel)
        class ViewModel(val released: String,
                        val runtime: String,
                        val genre: String,
                        val director: String,
                        val writer: String,
                        val actors: String,
                        val plot: String,
                        val rating: String)
    }

    class ChangeFavorite {
        class Request(val isFavorite: Boolean)
        class Result(val isSuccess: Boolean, val isFavorite: Boolean)
        class ViewModel(val isSuccess: Boolean, val isFavorite: Boolean)
    }
}

interface DetailInteractorLogic: InteractorLogic {
    fun requestTitle(request: DetailUserCases.DetailTitle.Request)
    fun requestInformation(request: DetailUserCases.DetailInformation.Request)
    fun requestFavoriteChange(request: DetailUserCases.ChangeFavorite.Request)
}

interface DetailPresenterLogic: PresenterLogic {
    fun presentTitle(response: DetailUserCases.DetailTitle.Result)
    fun presentDetailResponse(response: DetailUserCases.DetailInformation.Result)
    fun presentFavoriteChange(response: DetailUserCases.ChangeFavorite.Result)
}

interface DetailViewLogic: ViewLogic {
    fun displayTitle(viewModel: DetailUserCases.DetailTitle.ViewModel)
    fun displayInformation(viewModel: DetailUserCases.DetailInformation.ViewModel)
    fun displayFavoriteChange(viewModel: DetailUserCases.ChangeFavorite.ViewModel)
}

interface DetailDataRecover {
    val movie: Serializable
}