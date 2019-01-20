package com.dasilvacarlos.moviesstand.presentation.scenes.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.presentation.generic.GenericFragment


class MyListFragment: GenericFragment(), MyListView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_list, container, false)
    }
}