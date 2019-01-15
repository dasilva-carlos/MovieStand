package com.dasilvacarlos.moviesstand.presentation.generic

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


abstract class GenericActivity: AppCompatActivity(), GenericView {

    var lastToast: Toast? = null

    override fun displayError(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)

        lastToast?.cancel()
        lastToast = toast

        toast.show()
    }

    override fun hideKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}