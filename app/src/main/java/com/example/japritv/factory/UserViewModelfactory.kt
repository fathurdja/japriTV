package com.example.japritv.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.japritv.dao.AppDatabase
import com.example.japritv.viewmodel.UserViewModel

class UserViewModelfactory(private val db : AppDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}