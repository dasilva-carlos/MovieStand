package com.dasilvacarlos.moviesstand.presentation.generic

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

abstract class GenericFragment: Fragment(), GenericView {

    override fun displayError(message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)

        if (activity is GenericActivity) {
            (activity as? GenericActivity)?.lastToast?.cancel()
            (activity as? GenericActivity)?.lastToast = toast
        }

        toast.show()
    }

    override fun hideKeyboard() {
        val inputMethodManager = activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus.windowToken, 0)
    }
}