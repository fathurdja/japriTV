package com.example.japritv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.japritv.Repository.VideoRepository
import com.example.japritv.model.ResponseVideo
import com.example.japritv.model.Video
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel(private val dataRepository: VideoRepository) : ViewModel() {

    private val _videoList = MutableStateFlow<List<Video>>(emptyList())
    val videoList: StateFlow<List<Video>> = _videoList

    fun fetchData(onResult: (ResponseVideo?) -> Unit) {
        viewModelScope.launch {
            val response = dataRepository.fetchData()
            onResult(response)
        }
    }

}
