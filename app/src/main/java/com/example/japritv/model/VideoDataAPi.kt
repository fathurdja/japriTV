package com.example.japritv.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class VideoDataApi(
    @SerialName("_id") val id: String,
    val title: String,
    @SerialName("creator") val userId: String,
    val totalView: Int = 0,
    val totalSearch: Int = 0,
    val totalSales: Int? = null,  // Ubah menjadi nullable
    val totalSize: Int = 0,
    val releaseAt: String? = null, // Ubah menjadi nullable
    val isRelease: Boolean = false,
    val video: List<Video> = emptyList(),
    val poster: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val price: Int = 0,
    val totalEpisode: Int = 0

)