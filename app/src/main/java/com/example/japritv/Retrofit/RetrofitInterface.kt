package com.example.japritv.Retrofit

import com.example.japritv.model.ResponseVideo
import retrofit2.http.GET

interface RetrofitInterface {
    @GET("your_endpoint_here")
    suspend fun getResponseDataVideo():ResponseVideo
}