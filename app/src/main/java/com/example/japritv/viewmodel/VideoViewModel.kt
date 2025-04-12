package com.example.japritv.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.japritv.Repository.ProfileRepository
import com.example.japritv.Repository.VideoRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.dao.VideoData
import com.example.japritv.dao.historyEntity


import com.example.japritv.model.ResponseVideo
import com.example.japritv.model.Video
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class VideoViewModel(private val videoRepository: VideoRepository, private val db: AppDatabase) : ViewModel() {

    private val _dataList = MutableStateFlow<List<VideoData>>(emptyList())
    val dataList: StateFlow<List<VideoData>> = _dataList

    private val _selectedVideo = MutableStateFlow<VideoData?>(null)
    val selectedVideo: StateFlow<VideoData?> = _selectedVideo

    private val _mostViewedVideos = MutableStateFlow<ResponseVideo?>(null)
    val mostViewedVideos: StateFlow<ResponseVideo?> = _mostViewedVideos

    private val _mostSearchVideos = MutableStateFlow<ResponseVideo?>(null)
    val mostSearchVideos: StateFlow<ResponseVideo?> = _mostSearchVideos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _poster = MutableStateFlow<String?>(null)
    val poster: StateFlow<String?> = _poster

    val historyList: Flow<List<historyEntity>> = db.historyDao().getAllHistory()
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
               ignoreUnknownKeys = true  // Ignore unknown fields in the response
                isLenient = true           // Allow flexible parsing
                prettyPrint = true
            })
        }
    }
    fun saveToHistory(videoId: String, title: String, episode: Int,idgroup:String,idPoster:String) {
        viewModelScope.launch {
            val history = historyEntity(
                idGroup = idgroup ,
                title = title,
                poster = idPoster,
                episode = episode,
                idVideo = videoId,
                timestamp = System.currentTimeMillis()
            )
            db.historyDao().insertHistory(history)
        }
    }
    fun likeVideo(db: AppDatabase,idVideo: String){
        viewModelScope.launch {
            ProfileRepository.likeVideo(db,idVideo)
        }
    }

    fun getPoster(id: String) {
        viewModelScope.launch {
            _poster.value = videoRepository.getPosterById(id) // ✅ Ambil hanya poster
        }
    }
    fun fetchVideoById(id: String) {
        viewModelScope.launch {
            val video = videoRepository.getVideoByGroupId(id)
            _selectedVideo.value = video  // ✅ Simpan hasil ke StateFlow
        }
    }
    // Di VideoViewModel.kt


//    fun fetchMostSearchVideos() {
//        viewModelScope.launch {
//            val response = videoRepository.getMostSearchVideo(db)
//            _mostSearchVideos.value = response // ✅ Simpan hasil response ke StateFlow
//        }
//    }
//    fun fetchMostViewedVideos() {
//        viewModelScope.launch {
//            val response = videoRepository.getMostViewedVideo(db)
//            _mostViewedVideos.value = response // ✅ Simpan hasil response ke StateFlow
//        }
//    }



    fun fetchVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            // Ambil data dari Room sebagai fallback awal
            val videosFromRoom = videoRepository.getAllVideos()
            _dataList.value = videosFromRoom

            try {
                val url = URL("https://tv.japrime.id/video")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("Accept", "application/json")
                connection.connectTimeout = 10000
                connection.readTimeout = 10000

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val responseStream = connection.inputStream.bufferedReader().use { it.readText() }

                    val json = Json { ignoreUnknownKeys = true }
                    val response: ResponseVideo = json.decodeFromString(responseStream)
                    if (response.data.isNotEmpty()) {
                        println("Data dari API berhasil diambil: ${response.data.size} video")

                        val isDifferent = videosFromRoom.size != response.data.size ||
                                videosFromRoom.zip(response.data).any { (roomVideo, apiVideo) ->
                                    roomVideo.groupid != apiVideo.idgroup ||
                                            roomVideo.title != apiVideo.title ||
                                            roomVideo.video.size != apiVideo.video.size ||
                                            roomVideo.video.zip(apiVideo.video).any { (roomEp, apiEp) ->
                                                roomEp.id != apiEp.id ||
                                                        roomEp.episode != apiEp.episode ||
                                                        roomEp.view != apiEp.view ||
                                                        roomEp.like != apiEp.like
                                            }
                                }


                        if (isDifferent) {
                            println("Ada perubahan data, memperbarui Room Database...")
                            videoRepository.clearVideos()
                            videoRepository.saveVideoData(response)

                            // Ambil ulang data terbaru dari Room
                            _dataList.value = videoRepository.getAllVideos()
                        } else {
                            println("Data dari API sama dengan yang ada di Room, tidak perlu update.")
                        }
                    } else {
                        println("API Error: ${response.message}")
                    }
                } else {
                    println("Server Error: $responseCode - ${connection.responseMessage}")
                }
                connection.disconnect()
            } catch (e: Exception) {
                println("Network Error: ${e.message}, menggunakan data dari Room sebagai fallback.")
            }

            _isLoading.value = false
        }
    }





    override fun onCleared() {
        super.onCleared()
        client.close()  // Clean up client when ViewModel is cleared
    }


}
