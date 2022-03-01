package com.example.movies.data.NetWork

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("uploads/{photoName}")
    fun getImage(@Path("photoName") name : String) : Call<ResponseBody>

    @Multipart
    @POST("users/{username}")
    fun uploadImage(@Path("username") path : String, @Part part : MultipartBody.Part) : Call<RequestBody>
}