package com.dasilvacarlos.moviesstand.presentation.helpers

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.IdRes


class ColorStateListBuilder(context: Context) {
    private var colorDefault: Int? = null
    private var colorStateChecked: Int? = null
    private val context = context

    fun setColorDefault(@ColorRes color: Int): ColorStateListBuilder {
        colorDefault = context.resources.getColor(color)
        return this
    }

    fun setColorStateChecked(@ColorRes color: Int): ColorStateListBuilder {
        colorStateChecked = context.resources.getColor(color)
        return this
    }

    fun build(): ColorStateList {
        val states = mutableListOf<IntArray>()
        val colors = mutableListOf<Int>()

        colorStateChecked?.let {
            states.add(intArrayOf(android.R.attr.state_checked))
            colors.add(it)
        }

        colorDefault?.let{
            states.add(intArrayOf())
            colors.add(it)
        }

        return ColorStateList(states.toTypedArray(), colors.toIntArray())
    }
}