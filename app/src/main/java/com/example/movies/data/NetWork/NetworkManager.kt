package com.example.movies.data.NetWork

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {


    val retrofit : Retrofit? = null
    get() = field ?: Retrofit.Builder().baseUrl("http://51.195.19.222/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit?.create(ApiInterface::class.java)
}