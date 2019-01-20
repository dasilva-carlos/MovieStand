package com.dasilvacarlos.moviesstand.presentation.generic

import android.app.Application
import com.activeandroid.ActiveAndroid




class MovieStandApplication: Application() {
    companion object {
        var instance: MovieStandApplication = MovieStandApplication()
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        ActiveAndroid.initialize(this)
    }
}