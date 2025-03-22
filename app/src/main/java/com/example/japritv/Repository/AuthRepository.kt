package com.example.japritv.Repository

import android.util.Log
import com.example.japritv.dao.AppDatabase
import com.example.japritv.dao.LoginInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object AuthRepository {
    suspend fun sendTokenToServer(idToken: String, db: AppDatabase, nama: String, profile: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {

                val url = URL("https://japritv.vercel.app/api/auth/google")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", "Bearer $idToken")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doInput = true

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("AuthRepository", "Response Code: $responseCode")
                Log.d("AuthRepository", "Response Body: $responseMessage")

                if (responseCode == 200) {
                    val success = getDataLogin(db, idToken, nama, profile) // Menunggu hasil sebelum menyimpan
                    if (success) {
                        return@withContext true
                    }
                }
                return@withContext false
            } catch (e: Exception) {
                Log.e("AuthRepository", "Gagal mengirim token ke server", e)
                return@withContext false
            }
        }
    }

    private suspend fun getDataLogin(db: AppDatabase, idToken: String, nama: String, profile: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://japritv.vercel.app/api/user/profile")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", "Bearer $idToken")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doInput = true

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("AuthRepository", "Response Code: $responseCode")
                Log.d("AuthRepository", "Response Body: $responseMessage")

                if (responseCode == 200) {
                    val jsonResponse = JSONObject(responseMessage)
                    val data = jsonResponse.getJSONObject("data")
                    val email = data.getString("email")
                    val userId = data.getString("_id")
                    val coins = data.getInt("coins")
                    val createdAt = data.getString("createdAt")
                    val updatedAt = data.getString("updatedAt")
                    val referral = data.getString("referral")

                    val loginInfo = LoginInfo(
                        tokenAuth = idToken,
                        name = nama,
                        urlPicture = profile,
                        infoRegistrasi = "Berhasil Login",
                        email = email,
                        userId = userId,
                        coins = coins,
                        createdAt = createdAt,
                        updatedAt = updatedAt,
                        referral = referral
                    )
                    Log.d("AuthRepository", "Saving login info: $loginInfo")
                    db.loginInfoDao().saveLoginInfo(loginInfo)
                    return@withContext true
                } else if (responseCode == 400) {
                    Log.d("AuthRepository", "Token expired, refreshing token")
                    sendTokenToServer(idToken, db, nama, profile) // Refresh token dan coba lagi
                }
                return@withContext false
            } catch (e: Exception) {
                Log.e("AuthRepository", "Gagal mendapatkan data login", e)
                return@withContext false
            }
        }
    }
}


