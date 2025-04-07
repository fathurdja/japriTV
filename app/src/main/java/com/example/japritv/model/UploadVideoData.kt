package com.example.japritv.model

data class UploadVideoResponse(
    val code: Int = 0,
    val success: Boolean = false,
    val data: List<UploadVideoData> = emptyList()
)

data class UploadVideoData(
    val _id: String = "",
    val title: String = "",
    val poster: Poster? = null,
    val video: List<Video>? = emptyList(),
    val total_size: Int = 0,
    val total_episode: Int = 0,
    val release: Boolean = false,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val __v: Int = 0,

    // Optional field kalau suatu saat muncul di response
    val price: Int = 0
)

data class Poster(
    val id: String = "",
    val url: String = ""
)


