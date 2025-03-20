package com.example.japritv.Repository

import com.example.japritv.dao.VideoDao
import com.example.japritv.dao.VideoData
import com.example.japritv.model.ResponseVideo
import com.example.japritv.model.Video
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class VideoRepository(private val videoDao: VideoDao) {

    // Function to save data from the API
    suspend fun saveVideoData(response: ResponseVideo) {
        val videoDataList = response.data.map { videoData ->
            VideoData(
                id = videoData.id,
                title = videoData.title,
                userId = videoData.userId,
                totalSize = videoData.totalSize,
                videos = videoData.videos,  // ✅ Tidak perlu encode JSON lagi, langsung simpan sebagai List
                createdAt = videoData.createdAt,
                updatedAt = videoData.updatedAt,
                price = videoData.price,
                totalEpisode = videoData.totalEpisode
            )
        }
        videoDataList.forEach { videoDao.insertVideoData(it) }
    }

    suspend fun getAllVideos(): List<VideoData> {
        return videoDao.getAllVideoData()  // ✅ Langsung ambil data, tanpa decode manual
    }
    suspend fun getVideoById(id: String): VideoData? {
        return videoDao.getVideoById(id)  // ✅ Ambil data dari Room
    }

    suspend fun clearVideos() {
        return videoDao.clearVideos()
    }

    // Function to fetch all video data
//    suspend fun getAllVideos(): List<VideoData> {
//        val rawVideos = videoDao.getAllVideos()
//        return rawVideos.map { videoData ->
//            val decodedVideos: List<Video> = Json.decodeFromString(videoData.videos)  // ✅ Decode kembali JSON ke objek
//            videoData.copy(videos = decodedVideos.toString())  // ✅ Perbaiki agar tidak crash saat diakses
//        }
//    }

}
