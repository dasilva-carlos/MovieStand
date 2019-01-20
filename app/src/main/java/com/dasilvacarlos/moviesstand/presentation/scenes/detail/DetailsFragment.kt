package com.dasilvacarlos.moviesstand.presentation.scenes.detail

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.data.models.ResumedMovieModel
import com.dasilvacarlos.moviesstand.domain.app.detail.*
import com.dasilvacarlos.moviesstand.presentation.generic.GenericFragment
import com.dasilvacarlos.moviesstand.presentation.library.helpers.FavoriteStarHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.item_search_list.view.*
import kotlinx.android.synthetic.main.layout_inside_details.*
import java.io.Serializable


class DetailsFragment: GenericFragment(), DetailViewLogic, DetailDataRecover {

    companion object {
        private const val MOVIE_KEY = "MovieKey"

        fun getNewInstance(movie: MovieModel): DetailsFragment {
            val fragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable(MOVIE_KEY, movie)
            fragment.arguments = bundle
            return fragment
        }

        fun getNewInstance(movie: ResumedMovieModel): DetailsFragment {
            val fragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable(MOVIE_KEY, movie)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val interactor: DetailInteractorLogic = DetailInteractor(this, this)
    private lateinit var starHelper: FavoriteStarHelper

    override val movie: Serializable
        get() = getBundleInformation()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareFavoriteStar()
        requestInformation()
    }

    override fun onDestroyView() {
        interactor.cancelRequests()
        super.onDestroyView()
    }

    override fun displayTitle(viewModel: DetailUserCases.DetailTitle.ViewModel) {
        if(view != null) {
            Picasso.get()
                    .load(viewModel.banner)
                    .fit()
                    .centerCrop()
                    .error(R.drawable.ic_placeholder_image)
                    .into(detail_banner_image)
            collapsing_toolbar.title = viewModel.title

            collapsing_toolbar.setCollapsedTitleTypeface(ResourcesCompat.getFont(context!!, R.font.comfortaa_bold))
            collapsing_toolbar.setExpandedTitleTypeface(ResourcesCompat.getFont(context!!, R.font.comfortaa_bold))

            starHelper.statusChange(viewModel.isFavorite)
        }
    }

    override fun displayInformation(viewModel: DetailUserCases.DetailInformation.ViewModel) {
        if(view != null) {
            details_released_text.text = viewModel.released
            details_runtime_text.text = viewModel.runtime
            details_genre_text.text = viewModel.genre
            details_director_text.text = viewModel.director
            details_writer_text.text = viewModel.writer
            details_actors_text.text = viewModel.actors
            details_plot_text.text = viewModel.plot
            details_rating_text.text = viewModel.rating

            if (viewModel.director.contains(",")) {
                details_director_label.text = getString(R.string.details_directors)
            } else {
                details_director_label.text = getString(R.string.details_director)
            }

            if (viewModel.writer.contains(",")) {
                details_writer_label.text = getString(R.string.details_writers)
            } else {
                details_writer_label.text = getString(R.string.details_writer)
            }
        }
    }

    override fun displayFavoriteChange(viewModel: DetailUserCases.ChangeFavorite.ViewModel) {
        starHelper.statusChange(viewModel.isFavorite, viewModel.isSuccess)
        if(!viewModel.isSuccess) {
            displayError(getString(R.string.details_error_save))
        }
    }

    private fun requestInformation() {
        interactor.requestTitle(DetailUserCases.DetailTitle.Request())
        interactor.requestInformation(DetailUserCases.DetailInformation.Request())
    }

    private fun prepareFavoriteStar(){
        starHelper = FavoriteStarHelper(detail_star_image)
        detail_star_image.setOnClickListener {
            starHelper.animateWaitingForResult()
            interactor.requestFavoriteChange(DetailUserCases.ChangeFavorite.Request(!starHelper.isFilled))
        }
    }

    private fun getBundleInformation(): Serializable {
        return getBundle().getSerializable(MOVIE_KEY)
    }
}