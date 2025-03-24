package com.example.japritv.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.japritv.Repository.AuthRepository
import com.example.japritv.Repository.ProfileRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.dao.LoginInfo
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

    init {

        loadUserInfo()
    }

//    fun resetTokenUser(idToken: String) {
//        viewModelScope.launch {
//            val newToken = AuthRepository.resetToken(idToken)
//            _userInfo.value?.tokenAuth = newToken // Perbarui nilai tokenAuth
//        }
//    }

    fun loadSubscriptionInfo(idToken: String, db: AppDatabase, nama: String, profile: String) {
        viewModelScope.launch {
            val subscription = ProfileRepository.getDataSubscription(idToken, db, nama, profile)
            subscriptionInfo.value = subscription
        }
    }

    fun loadUserInfo() {
        viewModelScope.launch {
            _userInfo.value = loginInfoDao.getLoginInfo()
            println(_userInfo.value.toString())
        }
    }


    fun setNominal(nominal: Int) {
        _nominalState.value = nominal // Perbarui nilai state
    }


    fun setSelectedMembership(membership: String) {
        _selectedMembership.value = membership
    }

    fun setSubscriptionInfo(subscription: subscriptionData, token: String, db: AppDatabase) {
        viewModelScope.launch {
            ProfileRepository.deleteSubscription(idToken = token, id = subscription._id)
            val newsubscription = ProfileRepository.makeSubscription(
                idToken = token,
                level = subscription.level,
                db = db,
                nama = subscription.userId,
                profile = subscription.userId
            )
            newsubscriptionInfo.value = newsubscription
        }


    }

    fun makeSubscription(
        idToken: String,
        level: String,
        db: AppDatabase,
        nama: String,
        profile: String
    ) {
        viewModelScope.launch {
            ProfileRepository.makeSubscription(idToken, level, db, nama, profile)
        }
    }

    fun updateSubscriptionInfo(
        idToken: String,
        db: AppDatabase,
        nama: String,
        profile: String,
        id: String
    ) {
        viewModelScope.launch {
            val success = ProfileRepository.updateDataSubscription(id, idToken, db, nama, profile)
            if (success != null) {
                subscriptionInfo.value = success

            } else {
                subscriptionInfo.value = null
                Log.e("UserViewModel", "Failed to update subscription info")
            }
        }
    }

}