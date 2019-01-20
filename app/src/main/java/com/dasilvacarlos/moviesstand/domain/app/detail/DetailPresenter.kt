package com.dasilvacarlos.moviesstand.domain.app.detail

import com.dasilvacarlos.moviesstand.data.generics.ServiceError
import com.dasilvacarlos.moviesstand.domain.library.DateHelper


class DetailPresenter(val view: DetailViewLogic): DetailPresenterLogic {

    override fun presentTitle(response: DetailUserCases.DetailTitle.Result) {
        val viewModel = DetailUserCases.DetailTitle
                .ViewModel(response.title ?: "-",
                            response.banner ?: "-")
        view.displayTitle(viewModel)
    }

    override fun presentDetailResponse(response: DetailUserCases.DetailInformation.Result) {
        if(response.movie.response == true) {
            val released = DateHelper.convertDateFormat(
                    response.movie.released ?: "-",
                    DateHelper.DateFormatType.OMDB_FORMAT,
                    DateHelper.DateFormatType.DISPLAY_CALENDAR_FORMAT)

            val viewModel = DetailUserCases.DetailInformation
                    .ViewModel(released = released ?: "-",
                               runtime = response.movie.runtime ?: "-",
                               genre =  response.movie.genre ?: "-",
                               director = response.movie.director ?: "-",
                               writer = response.movie.writer ?: "-",
                               actors = response.movie.actors ?: "-",
                               plot = response.movie.plot ?: "-",
                               rating = response.movie.imdbRating ?: "-")

            view.displayInformation(viewModel)

        } else {
            view.displayError(DetailUserCases.DetailInformation.Request(), ServiceError.NOT_FOUND)
        }
    }

    override fun presentError(request: Any, serviceError: ServiceError) {
        view.displayError(request, error = serviceError)
    }
}