package com.example.japritv.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.japritv.dao.AppDatabase
import com.example.japritv.viewmodel.UploadEpisodeViewModel

class UploadEpisodeViewModelFactory(private val db : AppDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UploadEpisodeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UploadEpisodeViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}