package com.dasilvacarlos.moviesstand.data.generics

import retrofit2.Call
import retrofit2.Response


enum class WorkerError {
    CONNECTION_ERROR,
    NOT_FOUND,
    AUTHORIZATION_ERROR,
    UNKNOWN;

    companion object {
        fun <T> resolveError(response: Response<T>): WorkerError {
            return when(response.code()) {
                404 -> NOT_FOUND
                403, 401 -> AUTHORIZATION_ERROR
                else -> UNKNOWN
            }
        }

        fun resolveError(throwable: Throwable): WorkerError {
            return when (throwable) {
                else -> UNKNOWN
            }
        }
    }
}