package com.example.japritv.model
import com.example.japritv.dao.VideoData
import kotlinx.serialization.Serializable


@Serializable
data class ResponseVideo(
    val message: String = "",
    val data: List<VideoDataApi>
)


//@Serializable
//data class ResponseVideoList(
//    val data: List<VideoDataApi>
//)
//
//@Serializable
//data class ResponseVideoWrapper(
//    val code: Int,
//    val success: Boolean,
//    val data: ResponseVideoList
//)

