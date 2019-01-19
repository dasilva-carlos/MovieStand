package com.dasilvacarlos.moviesstand.presentation.scenes.search.adapter

import android.support.v7.widget.RecyclerView
import android.view.Gravity.TOP
import android.view.View
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.domain.search.SearchUserCases
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.ViewSkeletonScreen
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_search_list.view.*


class SearchItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun dataBind(viewModel: SearchUserCases.SearchForMovieTitle.ViewModel.Item) {
        Picasso.get()
                .load(viewModel.bannerUrl)
                .fit()
                .centerCrop()
                .error(R.drawable.ic_placeholder_image)
                .into(itemView.item_search_banner_image)

        itemView.item_search_movie_title_text.text = viewModel.title
        itemView.item_search_year_text.text = viewModel.year
    }
}