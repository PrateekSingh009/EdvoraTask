package com.example.edvoratask

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("rides")
    fun getData(): Call<List<ridesItem>>

    @GET("user")
    fun getUser(): Call<user>
}