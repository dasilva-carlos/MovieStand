package com.dasilvacarlos.moviesstand.presentation.scenes.list.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.domain.app.list.ListUserCases
import com.dasilvacarlos.moviesstand.presentation.scenes.favorites.adapter.FavoriteItemHolder


class ListAdapter (val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val HEADER_VIEW = 0
        const val ITEM_VIEW = 1
    }

    var itemClick: ((Int) -> (Unit))? = null
    private val viewModels: MutableList<ListUserCases.FavoritesList.ViewModel.Item> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == HEADER_VIEW) {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_header, parent, false)
            return ListHeaderHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list, parent, false)
            return ListItemHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = viewModels[position]
        return if(item is ListUserCases.FavoritesList.ViewModel.Header) HEADER_VIEW else ITEM_VIEW
    }

    override fun getItemCount(): Int {
        return viewModels.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ListHeaderHolder) {
            if (viewModels.count() > position) {
                val viewModel = viewModels[position]
                (viewModel as? ListUserCases.FavoritesList.ViewModel.Header)?.let {
                    holder.dataBind(viewModel = viewModel.text)
                }
            }
        }

        if(holder is ListItemHolder) {
            if (viewModels.count() > position) {
                val viewModel = viewModels[position]
                (viewModel as? ListUserCases.FavoritesList.ViewModel.Movie)?.let {
                    holder.dataBind(viewModel = viewModel.title)
                }
            }
        }
    }

    fun passViewModel(viewModel: ListUserCases.FavoritesList.ViewModel) {
        viewModel.items.let {
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