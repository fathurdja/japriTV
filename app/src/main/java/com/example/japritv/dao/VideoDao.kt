package com.example.japritv.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.japritv.model.Video
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideoData(videoData: VideoData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideoData(videoData: List<VideoData>) // Tambahkan fungsi untuk insert batch

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getVideoDataById(id: String): VideoData?
    @Query("DELETE FROM movie") // ✅ Tambahkan fungsi ini untuk menghapus semua data
    suspend fun clearVideos()
    @Query("SELECT * FROM movie")
    suspend fun getAllVideoData(): List<VideoData>

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getVideoById(id: String): VideoData?


//    suspend fun getAllVideos(): List<VideoData> {
//        val videoDataList = getAllVideoData()
//        return videoDataList.flatMap {
//            runCatching { Json.decodeFromString<List<VideoData>>(it.videos) }
//                .getOrElse { emptyList() } // Jika gagal decode, kembalikan list kosong
//        }
//    }
}
