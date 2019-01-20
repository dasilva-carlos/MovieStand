package com.dasilvacarlos.moviesstand.domain.app.detail

import com.dasilvacarlos.moviesstand.data.generics.ServiceError
import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.data.models.ResumedMovieModel
import com.dasilvacarlos.moviesstand.data.workers.detail.DetailProvider
import com.dasilvacarlos.moviesstand.data.workers.detail.DetailReceiver
import com.dasilvacarlos.moviesstand.data.workers.detail.DetailWorker


class DetailInteractor(view: DetailViewLogic, private val dataRecover: DetailDataRecover): DetailInteractorLogic, DetailReceiver {


    private val presenter: DetailPresenterLogic = DetailPresenter(view)
    private val worker: DetailProvider = DetailWorker(this)

    private var movieComplete : MovieModel? = null
    private var movieResumed: ResumedMovieModel? = null

    private val isDataRecovered: Boolean by lazy {recoverData()}

    override fun requestTitle(request: DetailUserCases.DetailTitle.Request) {
        if(isDataRecovered) {
            movieComplete?.let {
                val result = DetailUserCases.DetailTitle.Result(title = it.title, banner = it.poster)
                presenter.presentTitle(result)
                return
            }

            movieResumed?.let {
                val result = DetailUserCases.DetailTitle.Result(title = it.title, banner = it.poster)
                presenter.presentTitle(result)
            }
        }
    }

    override fun requestInformation(request: DetailUserCases.DetailInformation.Request) {
        if(isDataRecovered) {
            movieComplete?.let {
                val result = DetailUserCases.DetailInformation.Result(movie = it)
                presenter.presentDetailResponse(result)
                return
            }

            movieResumed?.let {
                worker.searchForMovieById(it.id ?: "")
            }
        }
    }

    override fun onReceiveResult(result: MovieModel) {
        movieComplete = result
        presenter.presentDetailResponse(DetailUserCases.DetailInformation.Result(movie = result))
    }

    override fun onError(error: ServiceError) {
        presenter.presentError(DetailUserCases.DetailInformation.Request(), error)
    }

    override fun cancelRequests() {
        worker.cancelRequests()
    }

    private fun recoverData(): Boolean {
        val movie = dataRecover.movie
        when(movie) {
            is MovieModel -> {
                movieComplete = movie
                return true
            }
            is ResumedMovieModel -> {
                movieResumed = movie
                return true
            }
        }

        return false
    }
}