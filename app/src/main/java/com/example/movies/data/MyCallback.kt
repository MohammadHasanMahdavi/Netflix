package com.example.movies.data

interface MyCallback <T> {
    fun onResponse(data : T)
    fun onfailure(t:Throwable)
}