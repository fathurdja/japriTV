package com.example.japritv.Repository

import android.util.Log
import com.example.japritv.dao.AppDatabase
import com.example.japritv.dao.AuthToken
import com.example.japritv.dao.LoginInfo
import com.example.japritv.model.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object AuthRepository {
    suspend fun sendTokenToServer(idToken: String, db: AppDatabase, nama: String, profile: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                println(idToken)
                val url = URL("https://tv.japrime.id/api/auth/google")
                val connection = url.openConnection() as HttpURLConnection
                connection.setRequestProperty("Authorization", idToken)
                connection.requestMethod = "GET"

                connection.setRequestProperty("Content-Type", "application/json")
                connection.doInput = true

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("AuthRepository", "Response Code: $responseCode")
                Log.d("AuthRepository", "Response Body: $responseMessage")
                val jsonObject= JSONObject(responseMessage)
                val tokenAcces = jsonObject.getString("data")
                db.authTokenDao().saveToken(
                    authToken = AuthToken(
                        id = 1,
                        token = tokenAcces
                    )
                )

                if (responseCode == 200) {
                    val success = getDataLogin(db, tokenAcces, nama, profile)
                    // Menunggu hasil sebelum menyimpan
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

    suspend fun getDataLogin(db: AppDatabase, idToken: String, namaUser: String, profile: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("AuthRepository", "Nama user dari parameter: $namaUser")
                val url = URL("https://tv.japrime.id/user/profile")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", idToken)
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
                    val coins = data.getInt("__v")
                    val createdAt = data.getString("createdAt")
                    val updatedAt = data.getString("updatedAt")
                    val referral = data.getString("referral")
                    Log.d("AuthRepository", "Saving login info: $namaUser")
                    val loginInfo = LoginInfo(
                        tokenAuth = idToken,
                        name = namaUser,
                        urlPicture = profile,
                        infoRegistrasi = "Berhasil Login",
                        email = email,
                        userId = userId,
                        coins = coins,
                        createdAt = createdAt,
                        updatedAt = updatedAt,
                        referral = referral
                    )


                    db.loginInfoDao().saveLoginInfo(loginInfo)
                    return@withContext true
                } else if (responseCode == 400) {
                    Log.d("AuthRepository", "Token expired, refreshing token")
                    sendTokenToServer(idToken, db, namaUser, profile) // Refresh token dan coba lagi
                }
                return@withContext false
            } catch (e: Exception) {
                Log.e("AuthRepository", "Gagal mendapatkan data login", e)
                return@withContext false
            }
        }
    }

    suspend fun resetToken(idToken: String, db: AppDatabase): String {
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
                    val newToken = idToken
                    db.authTokenDao().saveToken(authToken = AuthToken(id = 1, token = newToken))
                    Log.d("reset Token", "Berhasil reset")
                    return@withContext idToken
                } else {
                    Log.e("reset Token", "Gagal reset, Response Code: $responseCode, Message: $responseMessage")
                    return@withContext ""
                }
            } catch (e: Exception) {
                Log.e("reset Token", "Gagal reset, Exception: ${e.message}", e)
                return@withContext ""
            }
        }
    }


    fun extractToken(response: String): String {
        return try {
            val jsonObject = JSONObject(response)
            jsonObject.getString("token") // Pastikan key `"token"` sesuai dengan respons API
        } catch (e: JSONException) {
            Log.e("extractToken", "Gagal parsing JSON: ${e.message}")
            ""
        }
    }

}


