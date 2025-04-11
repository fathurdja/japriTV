package com.example.japritv.model

import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    @SerialName("id_video") val id: String,
    val episode: Int,
    val duration: Double, // ✅ tambahkan
    val view: Int = 0, // opsional, kosong kalau tidak ada
    val like: Int = 0 // opsional, kosong kalau tidak ada
)
