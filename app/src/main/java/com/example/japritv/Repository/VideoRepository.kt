package com.example.japritv.Repository

import android.util.Log
import com.example.japritv.dao.AppDatabase
import com.example.japritv.dao.VideoDao
import com.example.japritv.dao.VideoData
import com.example.japritv.model.PosterImg
import com.example.japritv.model.ResponseVideo
import com.example.japritv.model.Video
import com.example.japritv.model.VideoDataApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.Dispatcher
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class VideoRepository(private val videoDao: VideoDao) {

    // Function to save data from the API
    suspend fun saveVideoData(response: ResponseVideo) {
        val videoDataList = response.data.map { videoData ->
            VideoData(
                id = videoData.id,  // ID tidak boleh null
                title = videoData.title ?: "",
                userId = videoData.userId ?: "",
                createdAt = videoData.createdAt ?: "",
                updatedAt = videoData.updatedAt ?: "",
                price = videoData.price ?: 0,
                totalEpisode = videoData.totalEpisode ?: 0,
                totalView = videoData.totalView ?: 0,
                totalSearch = videoData.totalSearch ?: 0,
                totalSales = videoData.totalSales ?: 0,
                releaseAt = videoData.releaseAt ?: "",
                isRelease = videoData.isRelease ?: false,
                poster = videoData.poster?.url ?: "",
                video = videoData.video ?: emptyList(),
                totalSize = videoData.totalSize ?: 0
            )
        }

        videoDao.insertVideoData(videoDataList)// Batch insert untuk performa lebih baik
    }


    suspend fun getAllVideos(): List<VideoData> {
        return videoDao.getAllVideoData()  // ✅ Langsung ambil data, tanpa decode manual
    }

    suspend fun getVideoById(id: String): VideoData? {
        return videoDao.getVideoById(id)  // ✅ Ambil data dari Room
    }

    suspend fun getPosterById(id: String): String? {
        return videoDao.getPosterById(id)
    }

    suspend fun clearVideos() {
        return videoDao.clearVideos()
    }
    suspend fun getMostViewedVideo(db: AppDatabase, ): ResponseVideo {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token
                val url = URL("https://japritv-v2.vercel.app/api/video/mostviewed") // ✅ Perbaiki URL API
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", token) // ✅ Tambahkan "Bearer"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doInput = true

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("VideoRepository", "Response Code: $responseCode")
                Log.d("VideoRepository", "Response Body: $responseMessage")

                if (responseCode == 200) {
                    val jsonObject = JSONObject(responseMessage)
                    val dataArray = jsonObject.getJSONArray("data")

                    // Parsing JSON menjadi daftar objek VideoDataApi
                    val videoList = mutableListOf<VideoDataApi>()
                    for (i in 0 until dataArray.length()) {
                        val videoJson = dataArray.getJSONObject(i)
                        val videoItems = videoJson.getJSONArray("video")
                        val posterData = videoJson.getJSONObject("poster")
                        val posterImage = PosterImg(
                            id = posterData.getString("id"),
                            url = posterData.getString("url")
                        )

                        val videoListItems = mutableListOf<Video>()
                        for (j in 0 until videoItems.length()) {
                            val videoItem = videoItems.getJSONObject(j)
                            videoListItems.add(
                                Video(
                                    id = videoItem.getString("id"),
                                    episode = videoItem.getInt("episode"),
                                    url = videoItem.getString("url"),
                                    size = videoItem.getLong("size"),
                                    format = videoItem.getString("format"),
                                    duration = videoItem.getDouble("duration")
                                )
                            )
                        }

                        videoList.add(
                            VideoDataApi(
                                id = videoJson.getString("_id"),
                                title = videoJson.getString("title"),
                                userId = videoJson.getString("creator"),
                                totalView = videoJson.getInt("totalView"),
                                totalSearch = videoJson.getInt("totalSearch"),
                                totalSales = 0,
                                releaseAt = "",
                                isRelease = videoJson.getBoolean("isRelease"),
                                video = videoListItems,
                                poster = posterImage,
                                createdAt = videoJson.getString("createdAt"),
                                updatedAt = videoJson.getString("updatedAt"),
                                price = videoJson.getInt("price"),
                                totalEpisode = videoJson.getInt("totalEpisode"),
                                totalSize = videoJson.getInt("totalSize")
                            )
                        )
                    }

                    return@withContext ResponseVideo(message = "Success fetching most viewed videos",data = videoList)
                } else {
                    Log.e("VideoRepository", "API Error: Response Code $responseCode")
                    return@withContext ResponseVideo(message = "Failed fetching most viewed videos",data = emptyList())
                }
            } catch (e: Exception) {
                Log.e("VideoRepository", "Error fetching most viewed videos", e)
                return@withContext ResponseVideo(message = "Failed fetching most viewed videos",data = emptyList())
            }
        }
    }
    suspend fun getMostSearchVideo(db: AppDatabase, ): ResponseVideo {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token
                val url = URL("https://japritv-v2.vercel.app/api/video/mostsearch") // ✅ Perbaiki URL API
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", token) // ✅ Tambahkan "Bearer"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doInput = true

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("VideoRepository", "Response Code: $responseCode")
                Log.d("VideoRepository", "Response Body: $responseMessage")

                if (responseCode == 200) {
                    val jsonObject = JSONObject(responseMessage)
                    val dataArray = jsonObject.getJSONArray("data")

                    // Parsing JSON menjadi daftar objek VideoDataApi
                    val videoList = mutableListOf<VideoDataApi>()
                    for (i in 0 until dataArray.length()) {
                        val videoJson = dataArray.getJSONObject(i)
                        val videoItems = videoJson.getJSONArray("video")
                        val posterData = videoJson.getJSONObject("poster")
                        val posterImage = PosterImg(
                            id = posterData.getString("id"),
                            url = posterData.getString("url")
                        )
                        val videoListItems = mutableListOf<Video>()
                        for (j in 0 until videoItems.length()) {
                            val videoItem = videoItems.getJSONObject(j)
                            videoListItems.add(
                                Video(
                                    id = videoItem.getString("id"),
                                    episode = videoItem.getInt("episode"),
                                    url = videoItem.getString("url"),
                                    size = videoItem.getLong("size"),
                                    format = videoItem.getString("format"),
                                    duration = videoItem.getDouble("duration")
                                )
                            )
                        }

                        videoList.add(
                            VideoDataApi(
                                id = videoJson.getString("_id"),
                                title = videoJson.getString("title"),
                                userId = videoJson.getString("creator"),
                                totalView = videoJson.getInt("totalView"),
                                totalSearch = videoJson.getInt("totalSearch"),
                                totalSales = 0,
                                releaseAt = "",
                                isRelease = videoJson.getBoolean("isRelease"),
                                video = videoListItems,
                                poster = posterImage,
                                createdAt = videoJson.getString("createdAt"),
                                updatedAt = videoJson.getString("updatedAt"),
                                price = videoJson.getInt("price"),
                                totalEpisode = videoJson.getInt("totalEpisode"),
                                totalSize = videoJson.getInt("totalSize")
                            )
                        )
                    }

                    return@withContext ResponseVideo(message = "Success fetching most viewed videos",data = videoList)
                } else {
                    Log.e("VideoRepository", "API Error: Response Code $responseCode")
                    return@withContext ResponseVideo(message = "Failed fetching most viewed videos",data = emptyList())
                }
            } catch (e: Exception) {
                Log.e("VideoRepository", "Error fetching most viewed videos", e)
                return@withContext ResponseVideo(message = "Failed fetching most viewed videos",data = emptyList())
            }
        }
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
