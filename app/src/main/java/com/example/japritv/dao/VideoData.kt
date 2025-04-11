package com.example.japritv.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.japritv.model.Video
import kotlinx.serialization.Serializable

@Entity(tableName = "movie")
data class VideoData(
    @PrimaryKey val groupid: String,
    val title: String,
    val idPoster: String,
    val video : List<Video>,
)


