package com.dasilvacarlos.moviesstand.presentation.scenes.search.recommendationsAdapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.domain.app.search.SearchUserCases
import com.dasilvacarlos.moviesstand.presentation.scenes.favorites.adapter.FavoriteItemHolder


class RecommendationsAdapter (val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemClick: ((Int) -> (Unit))? = null
    private val viewModels: MutableList<SearchUserCases.Recommendations.ViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recommendation, parent, false)
        return RecommendationsHolder(view)
    }

    override fun getItemCount(): Int {
        return viewModels.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is  RecommendationsHolder) {
            if (viewModels.count() > position) {
                val viewModel = viewModels[position]
                holder.dataBind(viewModel)
                holder.configOnClick(onItemClick = View.OnClickListener { itemClick?.invoke(position) })
            }
        }
    }

    fun passViewModel(viewModel: SearchUserCases.Recommendations.ViewModel) {
        viewModels.add(viewModel)
        notifyItemInserted(viewModels.size - 1)
    }

    fun clear() {
        viewModels.clear()
        notifyDataSetChanged()
    }
}