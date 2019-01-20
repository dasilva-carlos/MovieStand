package com.dasilvacarlos.moviesstand.presentation.scenes.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_list_header.view.*


class ListHeaderHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    fun dataBind(viewModel: String) {
        itemView.item_list_title.text = viewModel
    }
}