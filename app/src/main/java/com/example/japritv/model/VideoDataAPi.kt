package com.example.japritv.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class VideoDataApi(
    @SerialName("_id") val id: String, // Gunakan id sebagai primary key
    val title: String,
    val userId: String,
    val totalSize: Long,
    val videos: List<Video>,
    val createdAt: String,
    val updatedAt: String,
    val price: Int,
    val totalEpisode: Int
)