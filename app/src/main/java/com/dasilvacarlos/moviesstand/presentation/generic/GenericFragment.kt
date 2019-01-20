package com.dasilvacarlos.moviesstand.presentation.generic

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.dasilvacarlos.moviesstand.data.generics.ServiceError

abstract class GenericFragment: Fragment(), GenericView {

    override fun displayError(request: Any, error: ServiceError) {
        context?.let {
            displayError(error.getDescription(it))
        }
    }

    override fun displayError(message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)

        if (activity is GenericActivity) {
            (activity as? GenericActivity)?.lastToast?.cancel()
            (activity as? GenericActivity)?.lastToast = toast
        }

        toast.show()
    }

    override fun hideKeyboard() {
        activity?.let {
            val inputMethodManager = it
                    .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.currentFocus.windowToken, 0)
        }
    }

    override fun getBundle(): Bundle {
        return arguments ?: Bundle()
    }
}