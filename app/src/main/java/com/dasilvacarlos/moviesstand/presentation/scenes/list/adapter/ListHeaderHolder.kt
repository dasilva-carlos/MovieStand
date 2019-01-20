package com.dasilvacarlos.moviesstand.presentation.scenes.list.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.dasilvacarlos.moviesstand.R
import kotlinx.android.synthetic.main.item_list_header.view.*
import java.lang.NumberFormatException


class ListHeaderHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    fun dataBind(context: Context, viewModel: String, isStars: Boolean)  {
        if(isStars) {
            itemView.item_list_title.visibility = View.GONE
            itemView.item_list_star_layout.visibility = View.VISIBLE
            try {
                setStars(context, viewModel.toInt())
            }catch (e:NumberFormatException){
                print(e.localizedMessage)
            }
        } else {
            itemView.item_list_title.visibility = View.VISIBLE
            itemView.item_list_star_layout.visibility = View.GONE
            itemView.item_list_title.text = viewModel
        }
    }


    fun setStars(context: Context, starsQuantity: Int){
        itemView.item_list_star_layout.removeAllViews()

        var margin = context.resources.getDimension(R.dimen.margin_extra_small).toInt()
        val totalSize = itemView.width - 24 * margin
        var starSize = totalSize/10

        if(starSize <= 0) {
            starSize = itemView.item_list_title.width / 10
            margin = 0
        }

        for(count in 1..starsQuantity) {
            val image = ImageView(context)
            val layoutParams = LinearLayout.LayoutParams(starSize, starSize)
            layoutParams.setMargins(margin,margin,margin,margin)
            image.layoutParams = layoutParams
            image.setColorFilter(context.resources.getColor(R.color.white))
            image.setImageResource(R.drawable.ic_star)
            itemView.item_list_star_layout.addView(image)
        }
    }
}