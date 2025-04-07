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
    suspend fun sendTokenToServer(idToken: String, db: AppDatabase): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://tv.japrime.id/auth/google")
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
                    val success = getDataLogin(db)
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

    suspend fun getDataLogin(db: AppDatabase): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val idToken = authInfo?.token
                val url = URL("https://tv.japrime.id/user/profile")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", idToken)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doInput = true

                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonResponse = JSONObject(responseMessage)

                val isSuccess = jsonResponse.optBoolean("success", false)
                if (isSuccess) {
                    val data = jsonResponse.getJSONObject("data")

                    val email = data.optString("email")
                    val role = data.optString("role")
                    val userId = data.optString("_id")
                    val coins = data.optInt("saldo")
                    val createdAt = data.optString("createdAt")
                    val updatedAt = data.optString("updatedAt")
                    val referral = data.optString("referral")
                    val nama = data.optString("name")
                    val picture = data.optString("picture")

                    val subscriptionJson = data.optJSONObject("subscription")
                    val subscriptionLevel = subscriptionJson?.optString("level")
                    val subscriptionStartDate = subscriptionJson?.optString("start_date")
                    val subscriptionEndDate = subscriptionJson?.optString("end_date")

                    val loginInfo = LoginInfo(
                        tokenAuth = idToken ?: "",
                        name = nama,
                        urlPicture = picture,
                        infoRegistrasi = "Berhasil Login",
                        email = email,
                        userId = userId,
                        saldo = coins,
                        createdAt = createdAt,
                        updatedAt = updatedAt,
                        referral = referral,
                        role = role,
                        subscriptionLevel = subscriptionLevel,
                        subscriptionStartDate = subscriptionStartDate,
                        subscriptionEndDate = subscriptionEndDate
                    )

                    db.loginInfoDao().saveLoginInfo(loginInfo)
                    return@withContext true
                } else {
                    Log.w("AuthRepository", "Login gagal: success = false")
                    return@withContext false
                }
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


