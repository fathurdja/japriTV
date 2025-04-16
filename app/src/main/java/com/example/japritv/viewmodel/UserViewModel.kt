package com.example.japritv.viewmodel

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.japritv.Repository.AuthRepository
import com.example.japritv.Repository.ProfileRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.dao.AuthToken
import com.example.japritv.dao.FcmToken
import com.example.japritv.dao.LoginInfo
import com.example.japritv.model.SignInResult

import com.example.japritv.model.UploadVideoData
import com.example.japritv.model.UploadVideoGroupData
import com.example.japritv.model.subscriptionData
import com.example.japritv.provider.GoogleAuthUiProvider
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(db: AppDatabase) : ViewModel() {

    private val loginInfoDao = db.loginInfoDao()

    private val _userInfo = MutableStateFlow<LoginInfo?>(null)
    val userInfo: StateFlow<LoginInfo?> = _userInfo
    val subscriptionInfo = MutableStateFlow<subscriptionData?>(null)
    private val _selectedMembership = MutableStateFlow<String?>(null)
    val selectedMembership: StateFlow<String?> = _selectedMembership
    private val _nominalState = MutableStateFlow(0) // Gunakan non-nullable Int
    val nominal: StateFlow<Int> = _nominalState
    val newsubscriptionInfo = MutableStateFlow<subscriptionData?>(null)

    private val _roleAccount= MutableStateFlow<String?>(null)
    val roleAccount: StateFlow<String?> = _roleAccount
    private val _selectedkoin = MutableStateFlow(0)
    val selectedkoin: StateFlow<Int> = _selectedkoin
    val isLoggedin = MutableStateFlow(false)

    init {
        loadUserInfo()
    }

    fun updateLoginState() {
        isLoggedin.value = _userInfo.value != null
    }
    fun loadSubscriptionInfo( db: AppDatabase,) {
        viewModelScope.launch {
            val subscription = ProfileRepository.getDataSubscription(db = db)
            subscriptionInfo.value = subscription
        }
    }
    fun getVideoUploaded(db: AppDatabase) {
        viewModelScope.launch {
        }
    }
    fun refreshDataLogin(db: AppDatabase){
        viewModelScope.launch {
         val success = AuthRepository.getDataLogin(db = db)
            if (success){
                loadUserInfo()
            }
        }
    }
    fun loadUserInfo() {
            viewModelScope.launch {
                _userInfo.value = loginInfoDao.getLoginInfo()
                updateLoginState() // Perbarui status login setelah mengambil data user
            }
        }
    fun getDataLogin(db: AppDatabase){
        viewModelScope.launch {
            AuthRepository.getDataLogin(db)
        }
    }

    fun clearMembership() {
        _selectedMembership.value = null
    }

    fun clearKoin() {
        _selectedkoin.value = 0
    }

    fun getRole(db: AppDatabase){
        viewModelScope.launch {
            _roleAccount.value = db.loginInfoDao().getRoleAccount()
        }
    }
    fun getUserRole(db: AppDatabase,onSuccess: () -> Unit,
                    onError: () -> Unit){
        viewModelScope.launch {
            val result = AuthRepository.registerCreator(db = db)
            if (result){
                loadUserInfo()
                onSuccess()
            }else{
                onError()
            }

        }
    }
    fun setKoin(jumlahKoin: Int){
        _selectedkoin.value = jumlahKoin
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
//            val newsubscription = ProfileRepository.makeSubscription(
//                level = subscription.level,
//                db = db,
//            )
//            newsubscriptionInfo.value = newsubscription
        }


    }
    fun register(token: String, db: AppDatabase){
        viewModelScope.launch {
            AuthRepository.sendTokenToServer(token,db)
        }
    }
    suspend fun onSignInResult(result: SignInResult, db: AppDatabase) {
        val data = result.data
        val token = data?.idToken
        if (token != null){
//            db.authTokenDao().saveToken(AuthToken(token = token))
            AuthRepository.sendTokenToServer(token,db)
        }else{
            Log.e("UserViewModel", "Token is null")
        }

    }

}