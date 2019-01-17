package com.dasilvacarlos.moviesstand.data.network.manager

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.google.gson.GsonBuilder
import com.google.gson.Gson




object RetrofitApiManager {
    private val BASE_API_URL = "http://www.omdbapi.com/"
    private val API_KEY = ""
    private var retrofit: Retrofit? = null
    private val gson = GsonBuilder().create()

    private val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)

    private val okHttpClient = okHttpClientBuilder.build()

    fun <T> createService(serviceClass: Class<T>): T {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
        }
        return retrofit!!.create(serviceClass)
    }

}