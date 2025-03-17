package com.example.japritv.model

import kotlinx.serialization.Serializable

@Serializable
data class Video(
    val title: String,
    val episode: Int,
    val url: String,
    val size: Long,
    val _id: String
)