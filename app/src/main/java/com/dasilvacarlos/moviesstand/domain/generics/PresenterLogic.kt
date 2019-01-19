package com.dasilvacarlos.moviesstand.domain.generics

import com.dasilvacarlos.moviesstand.data.generics.ServiceError


interface PresenterLogic {
    fun presentError(request: Any, serviceError: ServiceError)
}