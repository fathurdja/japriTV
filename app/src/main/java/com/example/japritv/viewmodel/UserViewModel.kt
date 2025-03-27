package com.example.japritv.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.japritv.Repository.AuthRepository
import com.example.japritv.Repository.ProfileRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.dao.LoginInfo
import com.example.japritv.model.UploadVideoData
import com.example.japritv.model.subscriptionData
import com.example.japritv.provider.GoogleAuthUiProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(db: AppDatabase) : ViewModel() {

    private val loginInfoDao = db.loginInfoDao()

    // StateFlow untuk menyimpan data LoginInfo
    private val _userInfo = MutableStateFlow<LoginInfo?>(null)
    val userInfo: StateFlow<LoginInfo?> = _userInfo
    val subscriptionInfo = MutableStateFlow<subscriptionData?>(null)
    private val _selectedMembership = MutableStateFlow<String?>(null)
    val selectedMembership: StateFlow<String?> = _selectedMembership
    private val _nominalState = MutableStateFlow(0) // Gunakan non-nullable Int
    val nominal: StateFlow<Int> = _nominalState
    val newsubscriptionInfo = MutableStateFlow<subscriptionData?>(null)
    private val _unreleasedVideos = MutableStateFlow<List<UploadVideoData>>(emptyList())
    val unreleasedVideos: StateFlow<List<UploadVideoData>> = _unreleasedVideos

    val isLoggedin = MutableStateFlow(false)

    init {
        loadUserInfo()
    }

    fun updateLoginState() {
        isLoggedin.value = _userInfo.value != null
    }

//

    fun loadSubscriptionInfo( db: AppDatabase,) {
        viewModelScope.launch {
            val subscription = ProfileRepository.getDataSubscription(db = db)
            subscriptionInfo.value = subscription
        }
    }


    fun getVideoUploaded(db: AppDatabase) {
        viewModelScope.launch {
            val videos = ProfileRepository.getVideoUploaded(db = db)
            _unreleasedVideos.value = videos
        }
    }


    fun loadUserInfo() {
            viewModelScope.launch {
                _userInfo.value = loginInfoDao.getLoginInfo()
                updateLoginState() // Perbarui status login setelah mengambil data user
            }
        }




    fun setNominal(nominal: Int) {
        _nominalState.value = nominal // Perbarui nilai state
    }


    fun setSelectedMembership(membership: String) {
        _selectedMembership.value = membership
    }

    fun setSubscriptionInfo(subscription: subscriptionData, db: AppDatabase) {
        viewModelScope.launch {
            subscriptionInfo.value?.let {
                ProfileRepository.deleteSubscription(
                    id = it._id,
                    db = db
                )
            }
            val newsubscription = ProfileRepository.makeSubscription(
                level = subscription.level,
                db = db,
            )
            newsubscriptionInfo.value = newsubscription
        }


    }

    fun makeSubscription(
        level: String,
        db: AppDatabase,
    ) {
        viewModelScope.launch {
            ProfileRepository.makeSubscription(level, db,)
        }
    }



    fun updateSubscriptionInfo(
        db: AppDatabase,
        id: String
    ) {
        viewModelScope.launch {
            val success = ProfileRepository.updateDataSubscription(id, db, )
            if (success != null) {
                subscriptionInfo.value = success

            } else {
                subscriptionInfo.value = null
                Log.e("UserViewModel", "Failed to update subscription info")
            }
        }
    }



}