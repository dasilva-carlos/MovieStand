package com.dasilvacarlos.moviesstand.presentation.scenes.favorites.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.domain.app.favorites.FavoritesUserCases
import com.dasilvacarlos.moviesstand.presentation.scenes.search.adapter.SearchItemHolder


class FavoritesAdapter (val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemClick: ((Int) -> (Unit))? = null
    private val viewModels: MutableList<FavoritesUserCases.FavoritesList.ViewModel.Item> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favorites, parent, false)
        return FavoriteItemHolder(view)
    }

    override fun getItemCount(): Int {
        return viewModels.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is FavoriteItemHolder) {
            if (viewModels.count() > position) {
                val viewModel = viewModels[position]
                holder.dataBind(viewModel)
                holder.configOnClick(onItemClick = View.OnClickListener { itemClick?.invoke(position) })
            }
        }
    }

    fun passViewModel(viewModel: FavoritesUserCases.FavoritesList.ViewModel) {
        viewModel.items?.let {
            viewModels.clear()
            viewModels.addAll(it.asIterable())
            notifyDataSetChanged()
        }
    }

    fun clear() {
        viewModels.clear()
        notifyDataSetChanged()
    }
}