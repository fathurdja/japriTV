package com.example.japritv.model

data class ApiResponse<T>(
    val message: String,
    val data: T?
)