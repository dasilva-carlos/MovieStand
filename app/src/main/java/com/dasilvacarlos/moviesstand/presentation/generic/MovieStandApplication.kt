package com.dasilvacarlos.moviesstand.presentation.generic

import android.app.Application


class MovieStandApplication: Application() {
    companion object {
        var instance: MovieStandApplication = MovieStandApplication()
    }

    init {
        instance = this
    }
}