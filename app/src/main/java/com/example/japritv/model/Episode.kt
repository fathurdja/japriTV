package com.example.japritv.model

data class Episode(
    val movieTitle: String = "Default Movie Title",
    val episodeTitle: String = "Episode 1",
    val fileName: String = "Klik untuk mengupload",
    val fileSize: String = "Maks. ukuran file: 75MB | Jenis file: MP4, MPG",
    val isUploading: Boolean = false,
    val progress: Float = 0f
)