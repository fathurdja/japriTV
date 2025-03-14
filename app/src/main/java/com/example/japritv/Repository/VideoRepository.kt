package com.example.japritv.Repository

import android.util.Log
import com.example.japritv.Retrofit.RetrofitInterface
import com.example.japritv.model.ResponseVideo

class VideoRepository(private val retrofitInterface: RetrofitInterface) {
    suspend fun fetchData(): ResponseVideo? {
        return try {
            val response = retrofitInterface.getResponseDataVideo()
            if (response.error) {
                null
            } else {
                response
            }
        } catch (e: Exception) {
            Log.e("VideoRepository", "Error fetching data: ${e.message}")
            null
        }
    }

}