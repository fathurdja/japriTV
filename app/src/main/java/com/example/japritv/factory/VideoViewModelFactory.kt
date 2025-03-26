package com.example.japritv.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.japritv.Repository.VideoRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.viewmodel.VideoViewModel

class VideoViewModelFactory(private val videoRepository: VideoRepository,private val db: AppDatabase) : ViewModelProvider.Factory {

    // Override the create method to return a VideoViewModel with the provided repository
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoViewModel::class.java)) {
            return VideoViewModel(videoRepository,db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
