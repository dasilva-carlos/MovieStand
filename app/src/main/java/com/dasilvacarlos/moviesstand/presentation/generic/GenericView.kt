package com.dasilvacarlos.moviesstand.presentation.generic

import com.dasilvacarlos.moviesstand.domain.generics.ViewLogic


interface GenericView: ViewLogic {
    fun displayError(message: String)
    fun hideKeyboard()
}