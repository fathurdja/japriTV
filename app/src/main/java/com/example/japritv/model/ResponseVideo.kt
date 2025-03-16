package com.example.japritv.model
import kotlinx.serialization.Serializable


@Serializable
data class ResponseVideo(
    val error: Boolean,
    val message: String,
    val data: List<DataItem>
)

@Serializable
data class DataItem(
    val _id: String,
    val title: String,
    val episode: Int,
    val url: String,
    val size: Int,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int,
    val uuid: String?, // Making uuid nullable
    val format: String? // Making format nullable
)