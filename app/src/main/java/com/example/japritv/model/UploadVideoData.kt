package com.example.japritv.model

data class UploadVideoData(
    val id: String,
    val price: Int,
    val totalEpisode: Int
)

data class UploadVideoResponse(
    val message: String,
    val data: List<UploadVideoItem>
)

data class UploadVideoItem(
    val _id: String,
    val isRelease: Boolean,
    val price: Int,
    val totalEpisode: Int
)
