package com.example.japritv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.japritv.dao.AppDatabase
import com.example.japritv.dao.LoginInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(db: AppDatabase) : ViewModel() {

    private val loginInfoDao = db.loginInfoDao()

    // StateFlow untuk menyimpan data LoginInfo
    private val _userInfo = MutableStateFlow<LoginInfo?>(null)
    val userInfo: StateFlow<LoginInfo?> = _userInfo

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            _userInfo.value = loginInfoDao.getLoginInfo()
        }
    }
}