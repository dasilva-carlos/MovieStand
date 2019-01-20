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
                     val banner: String?)
        class ViewModel(val title: String,
                        val banner: String)
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
}

interface DetailInteractorLogic: InteractorLogic {
    fun requestTitle(request: DetailUserCases.DetailTitle.Request)
    fun requestInformation(request: DetailUserCases.DetailInformation.Request)
}

interface DetailPresenterLogic: PresenterLogic {
    fun presentTitle(response: DetailUserCases.DetailTitle.Result)
    fun presentDetailResponse(response: DetailUserCases.DetailInformation.Result)
}

interface DetailViewLogic: ViewLogic {
    fun displayTitle(viewModel: DetailUserCases.DetailTitle.ViewModel)
    fun displayInformation(viewModel: DetailUserCases.DetailInformation.ViewModel)
}

interface DetailDataRecover {
    val movie: Serializable
}