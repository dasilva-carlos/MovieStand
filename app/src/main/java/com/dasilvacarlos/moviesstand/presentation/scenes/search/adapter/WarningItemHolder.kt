package com.dasilvacarlos.moviesstand.presentation.scenes.search.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_warning.view.*


class WarningItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun dataBind(viewModel: String) {
        itemView.item_warning_text.text = viewModel
    }
}