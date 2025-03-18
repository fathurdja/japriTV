package com.example.japritv.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.japritv.model.Video
import kotlinx.serialization.Serializable

@Entity(tableName = "movie")
@Serializable
data class VideoData(
    @PrimaryKey val id: String,
    val title: String,
    val userId: String,
    val totalSize: Long,
    val videos: List<Video>,  // Disimpan dalam format JSON
    val createdAt: String,
    val updatedAt: String,
    val price: Int,
    val totalEpisode: Int
)