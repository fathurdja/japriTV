package com.example.japritv.model

import android.graphics.Bitmap
import java.io.File

data class Episode(
    val movieTitle: String,
    val episodeTitle: String ,
    val fileName: File? ,
    val fileSize: String ,
    val isUploading: Boolean = false,
    val progress: Float = 0f,
    val thumbnail: Bitmap?
)