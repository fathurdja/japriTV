package com.example.japritv.model

data class Episode(
    val movieTitle: String,
    val episodeTitle: String ,
    val fileName: String ,
    val fileSize: String ,
    val isUploading: Boolean = false,
    val progress: Float = 0f
)