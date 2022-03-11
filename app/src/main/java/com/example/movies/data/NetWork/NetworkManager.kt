package com.example.movies.data.NetWork

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val retrofit : Retrofit? = null
    get() = field ?: Retrofit.Builder().baseUrl("http://51.195.19.222/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit?.create(ApiInterface::class.java)
}