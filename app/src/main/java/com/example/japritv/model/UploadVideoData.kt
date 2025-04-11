package com.example.japritv.model

data class UploadVideoResponse(
    val code: Int? = 0,
    val success: Boolean? = false,
    val data: UploadVideoGroupData? = null
)

data class UploadVideoGroupData(
    val id_group: String? = null,
    val id_poster: String? = null,
    val title: String? = null,
    val video: List<UploadVideoData>? = emptyList()
)

data class UploadVideoData(
    val id_video: String? = null,
    val episode: Int? = 0,
    val duration: Double? = 0.0,
    val view: Int? = 0,
    val like: Int? = 0
)




