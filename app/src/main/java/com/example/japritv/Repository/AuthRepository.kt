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
    fun sendTokenToServer(idToken: String, db: AppDatabase, nama:String,profile:String,callback: (Boolean) -> Unit,) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL("https://japritv.vercel.app/api/auth/google")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", "Bearer $idToken") // Kirim token sebagai header
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doInput = true

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("AuthRepository", "Response Code: $responseCode")
                Log.d("AuthRepository", "Response Body: $responseMessage")

                if (responseCode == 200) {
                    val jsonResponse = JSONObject(responseMessage)
                    val tokenAuth = idToken
                    val name = nama
                    val urlPicture = profile
                    val infoRegistrasi = jsonResponse.getString("message")
                    val email = jsonResponse.getString("email")

                    val loginInfo = LoginInfo(
                        tokenAuth = tokenAuth,
                        name = name,
                        urlPicture = urlPicture,
                        infoRegistrasi = infoRegistrasi,
                        email = email
                    )

                    db.loginInfoDao().saveLoginInfo(loginInfo)

                    withContext(Dispatchers.Main) {
                        callback(true)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        callback(false)
                    }
                }

            } catch (e: Exception) {
                Log.e("AuthRepository", idToken)
                Log.e("AuthRepository", "Gagal mengirim token ke server", e)
                withContext(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }


}

