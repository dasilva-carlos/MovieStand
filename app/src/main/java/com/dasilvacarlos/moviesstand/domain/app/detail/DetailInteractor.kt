package com.dasilvacarlos.moviesstand.domain.app.detail

import com.dasilvacarlos.moviesstand.data.generics.ServiceError
import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.data.models.ResumedMovieModel
import com.dasilvacarlos.moviesstand.data.workers.detail.DetailProvider
import com.dasilvacarlos.moviesstand.data.workers.detail.DetailReceiver
import com.dasilvacarlos.moviesstand.data.workers.detail.DetailWorker
import com.dasilvacarlos.moviesstand.data.workers.favorites.FavoritesProvider
import com.dasilvacarlos.moviesstand.data.workers.favorites.FavoritesReceiver
import com.dasilvacarlos.moviesstand.data.workers.favorites.FavoritesWorker


class DetailInteractor(view: DetailViewLogic, private val dataRecover: DetailDataRecover):
        DetailInteractorLogic, DetailReceiver {


    private val presenter: DetailPresenterLogic = DetailPresenter(view)
    private val worker: DetailProvider = DetailWorker(this)
    private val favoritesWorker: FavoritesProvider by lazy {FavoritesWorker(favoritesReceiver)}

    private var movieComplete : MovieModel? = null
    private var movieResumed: ResumedMovieModel? = null

    private val isDataRecovered: Boolean by lazy {recoverData()}

    override fun requestTitle(request: DetailUserCases.DetailTitle.Request) {
        if(isDataRecovered) {
            movieComplete?.let {
                favoritesWorker.checkIfIsFavorite(listOf(it.id ?: ""))
                return
            }

            movieResumed?.let {
                favoritesWorker.checkIfIsFavorite(listOf(it.id ?: ""))
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

    override fun requestFavoriteChange(request: DetailUserCases.ChangeFavorite.Request) {
        movieComplete?.let {
            if(request.isFavorite) {
                favoritesWorker.saveAsFavorite(it)
            } else {
                favoritesWorker.removeFavorite(it.id ?: "")
            }
            return
        }

        presenter.presentFavoriteChange(DetailUserCases.ChangeFavorite.Result(false, !request.isFavorite))
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

    private val favoritesReceiver = object : FavoritesReceiver {
        override fun onSaveResult(isSaved: Boolean, id: String?) {
            presenter.presentFavoriteChange(DetailUserCases.ChangeFavorite.Result(isSaved, isSaved))
        }

        override fun onRemoveResult(isRemoved: Boolean, id: String?) {
            presenter.presentFavoriteChange(DetailUserCases.ChangeFavorite.Result(isRemoved, !isRemoved))
        }

        override fun onFavoritesCheckResult(isFavorite: Map<String, Boolean>) {
            if(isDataRecovered) {
                movieComplete?.let {
                    val result = DetailUserCases.DetailTitle.Result(title = it.title,
                            banner = it.poster,
                            isFavorite = isFavorite[it.id] == true)
                    presenter.presentTitle(result)
                    return
                }

                movieResumed?.let {
                    val result = DetailUserCases.DetailTitle.Result(title = it.title,
                            banner = it.poster,
                            isFavorite = isFavorite[it.id] == true)
                    presenter.presentTitle(result)
                }
            }
        }
    }
}