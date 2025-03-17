package com.example.japritv.model
import kotlinx.serialization.Serializable


@Serializable
data class ResponseVideo(
    val message: String,
    val data: List<Data>
)

@Serializable
data class Data(
    val _id: String,
    val videos: List<Video>
)

