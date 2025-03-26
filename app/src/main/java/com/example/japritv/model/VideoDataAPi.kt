package com.example.japritv.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class VideoDataApi(
    @SerialName("_id") val id: String, // Gunakan id sebagai primary key
    val title: String,
    @SerialName("creator") val userId: String, // Menggunakan nama yang benar dari JSON
    val totalView: Int,
    val totalSearch: Int,
    val totalSales: Int,
    val releaseAt: String,
    val isRelease: Boolean,
    val video: List<Video>, // Gunakan "video" sesuai dengan JSON
    val poster: String, // Tambahkan poster karena ada di JSON
    val createdAt: String,
    val updatedAt: String,
    val price: Int,
    val totalEpisode: Int
)