package com.dasilvacarlos.moviesstand.presentation.scenes.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.presentation.generic.GenericFragment


class FavoritesFragment: GenericFragment(), FavoritesView {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_favorites, container, false)
    }
}