package com.example.japritv.factory



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.japritv.dao.AppDatabase
import com.example.japritv.viewmodel.PaymentViewModel
import com.example.japritv.viewmodel.UploadEpisodeViewModel

class PaymentViewModelFactory(private val db : AppDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PaymentViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}