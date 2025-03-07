package com.example.japritv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.japritv.model.Video
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {
    private val _currentVideo = MutableStateFlow<Video?>(null)
    val currentVideo: StateFlow<Video?> = _currentVideo

    init {
        // Inisialisasi video pertama kali agar `VideoScreen` langsung bisa memutar sesuatu
        _currentVideo.value = Video(
            id = "1",
            title = "Money Heist",
            videoUrl = "https://samplelib.com/lib/preview/mp4/sample-30s.mp4",
            thumbnailUrl = "https://thumbnail.jpg",
            duration = "50m",
            isPlaying = true
        )
    }

    fun playVideo(video: Video) {
        viewModelScope.launch {
            _currentVideo.value = video.copy(isPlaying = true)
        }
    }

    fun stopVideo() {
        viewModelScope.launch {
            _currentVideo.value = _currentVideo.value?.copy(isPlaying = false)
        }
    }

}
