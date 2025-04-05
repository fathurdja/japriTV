package com.example.japritv.viewmodel

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.japritv.Repository.AuthRepository
import com.example.japritv.Repository.ProfileRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.dao.LoginInfo
import com.example.japritv.dao.fcmToken
import com.example.japritv.model.UploadVideoData
import com.example.japritv.model.subscriptionData
import com.example.japritv.provider.GoogleAuthUiProvider
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.coroutineScope
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

    private val _fcmToken = MutableLiveData<String?>()
    val fcmToken: LiveData<String?> = _fcmToken

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
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

    fun getFCMToken(db: AppDatabase) {

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                _errorMessage.value = "Fetching FCM registration token failed: ${task.exception?.message}"
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            _fcmToken.value = token

            val dataTokenFcm = fcmToken(
                id = 1,
                fcmtoken = token
            )
            viewModelScope.launch {
                db.fcmToken().saveToken(dataTokenFcm)
            }

            // Log the token
            Log.d(TAG, "FCM Registration Token: $token")
        }
    }

    companion object {
        private const val TAG = "FCMTokenViewModel"
    }

}