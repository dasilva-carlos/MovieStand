package com.dasilvacarlos.moviesstand.presentation.scenes.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_list.view.*


class ListItemHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    fun dataBind(viewModel: String) {
        itemView.item_list_title.text = viewModel
    }

    fun configOnClick(onItemClick: View.OnClickListener) {
        itemView.setOnClickListener(onItemClick)
    }

    fun setDividerVisibility(isVisible: Boolean){
        itemView.item_list_divider.visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
    }
}