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
import com.example.japritv.Repository.VideoRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.dao.VideoData


import com.example.japritv.model.ResponseVideo
import com.example.japritv.model.Video
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

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

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
               ignoreUnknownKeys = true  // Ignore unknown fields in the response
                isLenient = true           // Allow flexible parsing
                prettyPrint = true
            })
        }
    }


    fun getPoster(id: String) {
        viewModelScope.launch {
            _poster.value = videoRepository.getPosterById(id) // ✅ Ambil hanya poster
        }
    }
    fun fetchVideoById(id: String) {
        viewModelScope.launch {
            val video = videoRepository.getVideoById(id)
            _selectedVideo.value = video  // ✅ Simpan hasil ke StateFlow
        }
    }

    fun fetchMostSearchVideos() {
        viewModelScope.launch {
            val response = videoRepository.getMostSearchVideo(db)
            _mostSearchVideos.value = response // ✅ Simpan hasil response ke StateFlow
        }
    }
    fun fetchMostViewedVideos() {
        viewModelScope.launch {
            val response = videoRepository.getMostViewedVideo(db)
            _mostViewedVideos.value = response // ✅ Simpan hasil response ke StateFlow
        }
    }
    fun fetchVideos() {
        viewModelScope.launch {


            // Ambil data dari Room
            val videosFromRoom = videoRepository.getAllVideos()
            _dataList.value=videosFromRoom



            try {
                val authInfo = db.loginInfoDao().getLoginInfo()
                val token = authInfo?.tokenAuth
                // Ambil data dari API
                val response: ResponseVideo = client.get("https://japritv-v2.vercel.app/api/video") {
                    headers {
                        if (token != null) {
                            append("Authorization", token)
                        }
                    }
                }.body()

                if (response.data.isNotEmpty()) {
                    println("Data dari API: $response")

                    // Bandingkan data API dengan data Room
                    if (videosFromRoom != response.data) {
                        _isLoading.value = true
                        println("Ada perubahan data, memperbarui Room Database...")

                        // Hapus data lama di Room
                        videoRepository.clearVideos()

                        // Simpan data terbaru ke Room
                        videoRepository.saveVideoData(response)

                        // Ambil ulang data terbaru dari Room
                        val freshVideos = videoRepository.getAllVideos()
                        _dataList.value = freshVideos
                    } else {
                        println("Data sudah up-to-date, tidak perlu update Room")
                        _dataList.value = videosFromRoom
                    }
                } else {
                    println("API Error: ${response.message}")
                }
            } catch (e: Exception) {
                println("Network Error: ${e.message}")

                // Jika terjadi error, gunakan data dari Room sebagai fallback
                _dataList.value = videosFromRoom
            }

            _isLoading.value = false
        }
    }


    override fun onCleared() {
        super.onCleared()
        client.close()  // Clean up client when ViewModel is cleared
    }


}
