package com.example.japritv.model

import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    val id: String,
    val episode: Int,
    val url: String,
    val size: Long,
    val duration: Double, // ✅ tambahkan
    val format: String = "" // opsional, kosong kalau tidak ada
)
