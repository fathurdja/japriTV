package com.example.japritv.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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

class VideoViewModel(private val videoRepository: VideoRepository) : ViewModel() {

    private val _dataList = MutableStateFlow<List<VideoData>>(emptyList())
    val dataList: StateFlow<List<VideoData>> = _dataList

    private val _selectedVideo = MutableStateFlow<VideoData?>(null)
    val selectedVideo: StateFlow<VideoData?> = _selectedVideo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
               ignoreUnknownKeys = true  // Ignore unknown fields in the response
                isLenient = true           // Allow flexible parsing
                prettyPrint = true
            })
        }
    }

    init {
        fetchVideos()
    }
    fun fetchVideoById(id: String) {
        viewModelScope.launch {
            val video = videoRepository.getVideoById(id)
            _selectedVideo.value = video  // ✅ Simpan hasil ke StateFlow
        }
    }
    private fun fetchVideos() {
        viewModelScope.launch {
            _isLoading.value = true

            // Ambil data dari Room
            val videosFromRoom = videoRepository.getAllVideos()

            if (videosFromRoom.isNotEmpty()) {
                _dataList.value = videosFromRoom  // Langsung assign ke StateFlow
            } else {
                // Jika tidak ada data di Room, ambil dari API
                try {
                    val response: ResponseVideo = client.get("https://api-japritv.vercel.app/api/video").body()

                    if (response.data.isNotEmpty()) {

                        println(response)
//                         Simpan data ke Room
                        videoRepository.saveVideoData(response)

                        // Ambil ulang data dari Room setelah penyimpanan
                        val freshVideos = videoRepository.getAllVideos()
                        _dataList.value = freshVideos
                    } else {
                        println("API Error: ${response.message}")
                    }
                } catch (e: Exception) {
                    println("Network Error: ${e.message}")
                }
            }

            _isLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        client.close()  // Clean up client when ViewModel is cleared
    }
}
