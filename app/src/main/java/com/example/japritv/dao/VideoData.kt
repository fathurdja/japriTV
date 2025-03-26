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
    val totalView: Int,
    val totalSearch: Int,
    val totalSales: Int,
    val releaseAt: String,
    val isRelease: Boolean,
    val poster: String,
    val video: List<Video>,
    val createdAt: String,
    val updatedAt: String,
    val price: Int,
    val totalEpisode: Int
)

