package com.dasilvacarlos.moviesstand.presentation.main

import android.content.res.ColorStateList
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.presentation.main.fragments.favorites.FavoritesFragment
import com.dasilvacarlos.moviesstand.presentation.main.fragments.search.SearchFragment
import com.dasilvacarlos.moviesstand.presentation.generic.GenericFragment
import com.dasilvacarlos.moviesstand.presentation.generic.MovieStandApplication
import com.dasilvacarlos.moviesstand.presentation.library.helpers.ColorStateListBuilder
import com.dasilvacarlos.moviesstand.presentation.main.fragments.list.MyListFragment


object NavigationHelper {
    val searchFragment: GenericFragment by lazy {
        SearchFragment()
    }

    val favoritesFragment: GenericFragment by lazy {
        FavoritesFragment()
    }

    val myListFragment: GenericFragment by lazy {
        MyListFragment()
    }

    val searchColor: ColorStateList by lazy {
        val context = MovieStandApplication.instance
        ColorStateListBuilder(context)
                .setColorDefault(R.color.white)
                .setColorStateChecked(R.color.blue)
                .build()
    }

    val favoritesColor: ColorStateList by lazy {
        val context = MovieStandApplication.instance
        ColorStateListBuilder(context)
                .setColorDefault(R.color.white)
                .setColorStateChecked(R.color.yellow)
                .build()
    }

    val myListColor: ColorStateList by lazy {
        val context = MovieStandApplication.instance
        ColorStateListBuilder(context)
                .setColorDefault(R.color.white)
                .setColorStateChecked(R.color.reddish)
                .build()
    }
}