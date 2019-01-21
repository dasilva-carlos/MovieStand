package com.dasilvacarlos.moviesstand.presentation.scenes.list.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
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
    var isStars = false
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
                    holder.dataBind(context, viewModel = viewModel.text, isStars = isStars)
                }
            }
        }

        if(holder is ListItemHolder) {
            if (viewModels.count() > position) {
                val viewModel = viewModels[position]
                (viewModel as? ListUserCases.FavoritesList.ViewModel.Movie)?.let {
                    holder.dataBind(viewModel = viewModel.title)
                    holder.configOnClick(onItemClick = View.OnClickListener {
                        itemClick?.invoke(resolveForIndex(position))
                    })

                    if(viewModels.count() <= position + 1 ||
                            viewModels[position+1] is ListUserCases.FavoritesList.ViewModel.Header){
                        holder.setDividerVisibility(false)
                    } else {
                        holder.setDividerVisibility(true)
                    }
                }
            }
        }
    }

    fun passViewModel(viewModel: ListUserCases.FavoritesList.ViewModel, isStars: Boolean) {
        this.isStars = isStars
        viewModels.clear()
        viewModels.addAll( viewModel.items.asIterable())
        notifyDataSetChanged()
    }

    fun clear() {
        viewModels.clear()
        notifyDataSetChanged()
    }

    private fun resolveForIndex(position: Int): Int {
        var count = 0
        viewModels.subList(0, position).forEach{ viewModel ->
            if(viewModel is ListUserCases.FavoritesList.ViewModel.Movie){
                count++
            }
        }
        return count
    }
}