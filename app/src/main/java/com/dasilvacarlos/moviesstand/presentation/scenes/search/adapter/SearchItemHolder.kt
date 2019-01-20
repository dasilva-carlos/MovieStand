package com.dasilvacarlos.moviesstand.presentation.scenes.search.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.domain.app.search.SearchUserCases
import com.dasilvacarlos.moviesstand.presentation.library.helpers.FavoriteStarHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_search_list.view.*


class SearchItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var starHelper: FavoriteStarHelper? = null

    fun dataBind(viewModel: SearchUserCases.SearchForMovieTitle.ViewModel.Item) {
        Picasso.get()
                .load(viewModel.bannerUrl)
                .fit()
                .centerCrop()
                .error(R.drawable.ic_placeholder_image)
                .into(itemView.item_search_banner_image)

        itemView.item_search_movie_title_text.text = viewModel.title
        itemView.item_search_year_text.text = viewModel.year
        starHelper = FavoriteStarHelper(itemView.item_search_star)
        starHelper?.statusChange(isSaved = viewModel.isFavorite, animated = false)
    }

    fun configOnClick(onItemClick: View.OnClickListener, onStarClick: ((Boolean) -> (Unit))) {
        itemView.item_search_card_view.setOnClickListener(onItemClick)
        itemView.item_search_star.setOnClickListener { _ ->
            starHelper?.isFilled?.let {
                starHelper?.animateWaitingForResult()
                onStarClick(!it)
            }
        }
    }
}