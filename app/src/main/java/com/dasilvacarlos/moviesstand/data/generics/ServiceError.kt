package com.dasilvacarlos.moviesstand.data.generics

import android.content.Context
import com.dasilvacarlos.moviesstand.R
import retrofit2.Response


enum class ServiceError {
    CONNECTION_ERROR,
    NOT_FOUND,
    AUTHORIZATION_ERROR,
    UNKNOWN;

    companion object {
        fun <T> resolveError(response: Response<T>): ServiceError {
            return when(response.code()) {
                404 -> NOT_FOUND
                403, 401 -> AUTHORIZATION_ERROR
                else -> UNKNOWN
            }
        }

        fun resolveError(throwable: Throwable): ServiceError {
            return when (throwable) {
                is java.net.UnknownHostException -> CONNECTION_ERROR
                else -> UNKNOWN
            }
        }
    }

    fun getDescription(context: Context): String {
        return when {
            this == CONNECTION_ERROR -> context.getString(R.string.connexion_error)
            this == NOT_FOUND -> context.getString(R.string.not_found_error)
            this == AUTHORIZATION_ERROR -> context.getString(R.string.authorization_error)
            else -> context.getString(R.string.unknown_error)
        }
    }
}