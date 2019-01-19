package com.dasilvacarlos.moviesstand.data.network.manager

import com.dasilvacarlos.moviesstand.data.network.apis.OmdbApi
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.google.gson.GsonBuilder


object RetrofitManager {

    private val retrofit: Retrofit by lazy {
        getRetrofitInstance()
    }
    private val okHttpClient by lazy {
        getHttpClient()
    }

    private fun getRetrofitInstance(): Retrofit {
        val gson = GsonBuilder().create()
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    private fun getHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}