package com.example.japritv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    init {
        loadUserInfo()
    }

     fun loadUserInfo() {
        viewModelScope.launch {
            _userInfo.value = loginInfoDao.getLoginInfo()
        }
    }
    fun setSelectedMembership(membership: String) {
        _selectedMembership.value = membership
    }
    fun makeSubscription(idToken: String, level: String,db: AppDatabase,nama: String, profile: String) {
        viewModelScope.launch {
            val success = ProfileRepository.makeSubscription(idToken, level,db,nama, profile)
            if (success) {
                updateSubscriptionInfo(idToken,db,nama, profile)
            }
        }
    }

 fun updateSubscriptionInfo(idToken: String, db: AppDatabase, nama: String, profile: String) {
        viewModelScope.launch {
            val success =  ProfileRepository.getDataSubscription(idToken, db , nama , profile)
            if (success != null) {
                    subscriptionInfo.value = success

            }else{
                subscriptionInfo.value = null
            }
        }
    }

}