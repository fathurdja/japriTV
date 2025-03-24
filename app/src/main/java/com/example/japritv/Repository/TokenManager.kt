package com.example.japritv.Repository

import com.example.japritv.dao.newAuthDao
import com.example.japritv.dao.newInterfaceAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TokenManager(private val authTokenDao: newInterfaceAuth) {

    suspend fun saveToken(token: String, expiresIn: Long) {
        val expiresAt = System.currentTimeMillis() + (expiresIn * 1000) // Convert detik ke milisecond
        val authToken = newAuthDao(token = token, expiresAt = expiresAt)
        withContext(Dispatchers.IO) {
            authTokenDao.saveToken(authToken)
        }
    }

    suspend fun getToken(): String? {
        return withContext(Dispatchers.IO) {
            authTokenDao.getToken()?.token
        }
    }

    suspend fun isTokenExpired(): Boolean {
        return withContext(Dispatchers.IO) {
            val expiresAt = authTokenDao.getToken()?.expiresAt ?: 0
            System.currentTimeMillis() > expiresAt
        }
    }

    suspend fun clearToken() {
        withContext(Dispatchers.IO) {
            authTokenDao.clearToken()
        }
    }
}
