package com.dasilvacarlos.moviesstand.presentation.generic

import android.support.v7.app.AppCompatActivity
import android.widget.Toast


open class GenericActivity: AppCompatActivity(), GenericView {
    var lastToast: Toast? = null

    override fun displayError(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)

        lastToast?.cancel()
        lastToast = toast

        toast.show()
    }
}