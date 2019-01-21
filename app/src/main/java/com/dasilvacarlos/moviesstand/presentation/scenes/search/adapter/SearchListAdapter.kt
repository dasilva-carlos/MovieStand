package com.dasilvacarlos.moviesstand.presentation.scenes.search.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.domain.app.search.SearchUserCases


class SearchListAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val WARNING_VIEW = 0
        const val ITEM_VIEW = 1
    }

    var itemClick: ((Int) -> (Unit))? = null
    var itemStarClick: ((Int, Boolean) -> (Unit))? = null
    private val viewModels: MutableList<SearchUserCases.SearchForMovieTitle.ViewModel.Item> = mutableListOf()
    private var errorMessage: String? = context.getString(R.string.search_type)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == WARNING_VIEW) {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_warning, parent, false)
            return WarningItemHolder(view)
        }

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_list, parent, false)
        return SearchItemHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (errorMessage == null) ITEM_VIEW else WARNING_VIEW
    }

    override fun getItemCount(): Int {
        return if(errorMessage != null) 1 else viewModels.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is SearchItemHolder) {
            if (viewModels.count() > position) {
                val viewModel = viewModels[position]
                holder.dataBind(viewModel)
                holder.configOnClick(
                        onItemClick = View.OnClickListener {itemClick?.invoke(position)},
                        onStarClick = object: (Boolean) -> (Unit) {
                            override fun invoke(p1: Boolean) {
                                itemStarClick?.invoke(position,p1)
                            }
                        }
                )
            }
        }

        if(holder is WarningItemHolder) {
            errorMessage?.let {
                holder.dataBind(it)
            }
        }
    }

    fun passViewModel(viewModel: SearchUserCases.SearchForMovieTitle.ViewModel) {
        when {
            viewModel.isError -> setErrorMessage(viewModel.errorMessage.orEmpty())
            else -> {
                viewModel.items?.let {
                    viewModels.addAll(it.asIterable())
                    errorMessage = null
                    notifyDataSetChanged()
                }
            }
        }
    }

    fun updateIsFavorite(viewModel: SearchUserCases.ChangeFavorite.ViewModel) {
        viewModels[viewModel.index].isFavorite = viewModel.isFavorite
        notifyItemChanged(viewModel.index)
    }

    fun updateIsFavorite(viewModel: SearchUserCases.CheckFavorite.ViewModel) {
        if(viewModels.count() > 0) {
            for (index in 0 until viewModels.count()) {
                if (viewModels[index].isFavorite != viewModel.isFavorite[index]) {
                    viewModels[index].isFavorite = viewModel.isFavorite[index]
                    notifyItemChanged(index)
                }
            }
        }
    }

    fun setErrorMessage(error: String) {
        if (errorMessage != error) {
            errorMessage = error
            notifyDataSetChanged()
        }
    }

    fun clearItems() {
        viewModels.clear()
        errorMessage = null
        notifyDataSetChanged()
    }
}