package com.example.japritv.viewmodel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.japritv.model.Data

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
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class VideoViewModel : ViewModel() {
     private val _dataList = MutableStateFlow<List<Data>>(emptyList())
    val dataList: StateFlow<List<Data>> = _dataList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val allVideos: StateFlow<List<Video>> = dataList.map { dataList ->
        dataList.flatMap { it.videos }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true  // Abaikan field yang tidak dikenal dalam respons
                isLenient = true           // Izinkan parsing yang lebih fleksibel
                prettyPrint = true
            })
        }
    }

    fun fetchVideos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response: ResponseVideo = client.get("https://api-japritv.vercel.app/api/video").body()
                println(response)
                if (response.data.isNotEmpty()) {
                    _dataList.value = response.data
                } else {
                    println("API Error: ${response.message}")
                }
            } catch (e: Exception) {
                println("Network Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        client.close()
    }
}

