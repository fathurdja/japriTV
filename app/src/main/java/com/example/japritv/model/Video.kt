package com.example.japritv.model

data class Video (
    val id: String,
    val title: String,
    val category: String,
    val episode: Long,
    val price: Long,
    val urlPath: String,
    val videoSize: Long,
    val userID: String
)
