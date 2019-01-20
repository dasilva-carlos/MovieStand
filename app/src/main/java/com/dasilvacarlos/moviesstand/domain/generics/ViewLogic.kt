package com.dasilvacarlos.moviesstand.domain.generics

import android.os.Bundle
import com.dasilvacarlos.moviesstand.data.generics.ServiceError


interface ViewLogic {
    fun displayError(request: Any, error: ServiceError)
    fun getBundle(): Bundle
}