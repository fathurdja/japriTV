package com.example.japritv.model

data class Video(
    val id: String,
    val title: String,
    val videoUrl: String,
    val thumbnailUrl: String,
    val duration: String,
    val isPlaying: Boolean = false
)
