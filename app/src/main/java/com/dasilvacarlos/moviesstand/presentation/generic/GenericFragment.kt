package com.dasilvacarlos.moviesstand.presentation.generic

import android.support.v4.app.Fragment
import android.widget.Toast

open class GenericFragment: Fragment(), GenericView {
    override fun displayError(message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)

        if (activity is GenericActivity) {
            (activity as? GenericActivity)?.lastToast?.cancel()
            (activity as? GenericActivity)?.lastToast = toast
        }

        toast.show()
    }
}