package com.dasilvacarlos.moviesstand.presentation.scenes.search.recommendationsAdapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.domain.app.search.SearchUserCases
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recommendation.view.*


class RecommendationsHolder(view: View): RecyclerView.ViewHolder(view) {
    fun dataBind(viewModel: SearchUserCases.Recommendations.ViewModel) {
        Picasso.get()
                .load(viewModel.bannerUrl)
                .fit()
                .centerCrop()
                .error(R.drawable.ic_placeholder_image)
                .into(itemView.item_recommendation_banner)

        itemView.item_recommendation_title.text = viewModel.title
    }

    fun configOnClick(onItemClick: View.OnClickListener) {
        itemView.item_favorites_card_view.setOnClickListener(onItemClick)
    }
}