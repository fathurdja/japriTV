package com.example.japritv.model

data class ResponseVideo(
    val error: Boolean,
    val message: String,
    val data: List<Video>
)