package com.example.japritv.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoDataApi(
    @SerialName("id_group") val idgroup: String,
    @SerialName("id_poster") val idposter: String,
    val title: String,
    val video: List<Video> = emptyList(),
)
