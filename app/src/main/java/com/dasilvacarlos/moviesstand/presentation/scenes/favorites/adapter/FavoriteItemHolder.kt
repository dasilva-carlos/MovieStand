package com.dasilvacarlos.moviesstand.presentation.scenes.favorites.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.domain.app.favorites.FavoritesUserCases
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_favorites.view.*


class FavoriteItemHolder(view: View): RecyclerView.ViewHolder(view) {
    fun dataBind(viewModel: FavoritesUserCases.FavoritesList.ViewModel.Item) {
        Picasso.get()
                .load(viewModel.bannerUrl)
                .fit()
                .centerCrop()
                .error(R.drawable.ic_placeholder_image)
                .into(itemView.item_favorites_banner)

        itemView.item_favorites_title.text = viewModel.title
    }

    fun configOnClick(onItemClick: View.OnClickListener) {
        itemView.item_favorites_card_view.setOnClickListener(onItemClick)
    }
}