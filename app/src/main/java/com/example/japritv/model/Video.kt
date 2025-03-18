package com.example.japritv.model

import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    @PrimaryKey val uuid: String,
    val episode: Int,
    val url: String,
    val size: Long
)
