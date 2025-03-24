package com.example.japritv.Repository

import android.util.Log
import com.example.japritv.Repository.AuthRepository.sendTokenToServer
import com.example.japritv.dao.AppDatabase
import com.example.japritv.model.subscriptionData
import com.example.japritv.provider.GoogleAuthUiProvider

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object ProfileRepository {
    suspend fun updateDataSubscription(
        _id: String,
        idToken: String,
        db: AppDatabase,
        nama: String,
        profile: String
    ): subscriptionData? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://japritv.vercel.app/api/user/subscription/${_id}")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", "Bearer $idToken")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doInput = true

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("GetDataProfileRepository", "Response Code: $responseCode")
                Log.d("GetDataProfileRepository", "Response Body: $responseMessage")

                if (responseCode == 200) {
                    val jsonResponse = JSONObject(responseMessage)
                    val data = jsonResponse.optJSONObject("data") ?: return@withContext null

                    return@withContext subscriptionData(
                        _id = data.getString("_id"),
                        userId = data.getString("userId"),
                        level = data.getString("level"),
                        startDate = data.getString("startDate"),
                        endDate = data.getString("endDate"),
                        isPayed = data.getBoolean("isPayed"),
                        isExpired = data.getBoolean("isExpired"),
                        price = data.getInt("price")
                    )
                } else if (responseCode == 400) {
                    Log.e("AuthRepo", "Token Expired")
                    sendTokenToServer(idToken, db, nama, profile)
                }
                return@withContext null
            } catch (e: Exception) {

                Log.e("ProfileRepository", "Error dalam getDataSubscription", e)
                return@withContext null
            }
        }
    }
    suspend fun getDataSubscription(
        idToken: String,
        db: AppDatabase,
        nama: String,
        profile: String
    ): subscriptionData? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://japritv.vercel.app/api/user/subscription/")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", "Bearer $idToken")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doInput = true

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("GetDataProfileRepository", "Response Code: $responseCode")
                Log.d("GetDataProfileRepository", "Response Body: $responseMessage")

                if (responseCode == 200) {
                    val jsonResponse = JSONObject(responseMessage)
                    val data = jsonResponse.optJSONObject("data") ?: return@withContext null

                    return@withContext subscriptionData(
                        _id = data.getString("_id"),
                        userId = data.getString("userId"),
                        level = data.getString("level"),
                        startDate = data.getString("startDate"),
                        endDate = data.getString("endDate"),
                        isPayed = data.getBoolean("isPayed"),
                        isExpired = data.getBoolean("isExpired"),
                        price = data.getInt("price")
                    )
                } else if (responseCode == 400) {
                    Log.e("AuthRepo", "Token Expired")
                    sendTokenToServer(idToken, db, nama, profile)
                }
                return@withContext null
            } catch (e: Exception) {
                Log.e("ProfileRepository", "Error dalam getDataSubscription", e)
                return@withContext null
            }
        }
    }

    suspend fun makeSubscription(
        idToken: String,
        level: String,
        db: AppDatabase,
        nama: String,
        profile: String
    ): subscriptionData? {
        return withContext(Dispatchers.IO) {

            try {
                sendTokenToServer(idToken, db, nama, profile)
                val url = URL("https://japritv.vercel.app/api/user/subscription")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", "Bearer $idToken")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val requestBody = JSONObject().apply {
                    put("level", level)
                }.toString()
                Log.d("ProfileRepository", "Request Body: $requestBody")
                connection.outputStream.use { outputStream ->
                    outputStream.write(requestBody.toByteArray())
                    outputStream.flush()
                }

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("ProfileRepository", "Response Code: $responseCode")
                Log.d("ProfileRepository", "Response Body: $responseMessage")

                if (responseCode == 200) {
                    val jsonResponse = JSONObject(responseMessage)
                    val data = jsonResponse.optJSONObject("data") ?: return@withContext null

                    return@withContext subscriptionData(
                        _id = data.getString("_id"),
                        userId = data.getString("userId"),
                        level = data.getString("level"),
                        startDate = data.getString("startDate"),
                        endDate = data.getString("endDate"),
                        isPayed = data.getBoolean("isPayed"),
                        isExpired = data.getBoolean("isExpired"),
                        price = data.getInt("price")
                    )

                } else if (responseCode == 400) {
                    Log.e("AuthRepo", "Token Expired")
                    sendTokenToServer(idToken, db, nama, profile)
                }
                return@withContext null
            } catch (e: Exception) {
                Log.e("ProfileRepository", "Gagal membuat subscription", e)

                return@withContext null
            }
        }
    }

    suspend fun makeDataTransaction(
        idToken: String,
        amount: Int,
        idCreator: String,
        db: AppDatabase,
        nama: String,
        profile: String
    ): Boolean {
        return withContext(Dispatchers.IO) {

            try {
                sendTokenToServer(idToken, db, nama, profile)
                val url = URL("https://japritv.vercel.app/api/user/transaction")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", "Bearer $idToken")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val requestBody = JSONObject().apply {
                    put("creatorId", idCreator)  // Replace with actual creatorId
                    put("amount", amount)  // Replace with actual amount if needed
                }.toString()

                Log.d("ProfileRepository", "Request Body: $requestBody")
                connection.outputStream.use { outputStream ->
                    outputStream.write(requestBody.toByteArray())
                    outputStream.flush()
                }

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("ProfileRepository", "Response Code: $responseCode")
                Log.d("ProfileRepository", "Response Body: $responseMessage")

                if (responseCode == 200) {
                    Log.d("ProfileRepository", "Response Body: $responseMessage")
                    return@withContext getDataTransaction(
                        idToken,
                        amount,
                        idCreator,
                        db,
                        nama,
                        profile
                    )
                } else if (responseCode == 400) {
                    Log.d("ProfileRepository", "Response Body: $responseMessage")
                    Log.e("AuthRepo", "Token Expired")
                    sendTokenToServer(idToken, db, nama, profile)
                }
                return@withContext false
            } catch (e: Exception) {
                Log.e("ProfileRepository", "Gagal membuat transaksi ", e)

                return@withContext false
            }
        }
    }

    suspend fun getDataTransaction(
        idToken: String,
        amount: Int,
        idCreator: String,
        db: AppDatabase,
        nama: String,
        profile: String
    ): Boolean {
        return withContext(Dispatchers.IO) {

            try {
                sendTokenToServer(idToken, db, nama, profile)
                val url = URL("https://japritv.vercel.app/api/user/transaction")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", "Bearer $idToken")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true


                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("GetDataProfileRepository", "Response Code: $responseCode")
                Log.d("GetDataProfileRepository", "Response Body: $responseMessage")

                if (responseCode == 200) {
//                    Log.d("ProfileRepository", "Response Body: $responseMessage")
//                    val jsonResponse = JSONObject(responseMessage)
//                    val data = jsonResponse.optJSONObject("data") ?: return@withContext null

//                    return@withContext subscriptionData(
//                        _id = data.getString("_id"),
//                        userId = data.getString("userId"),
//                        level = data.getString("level"),
//                        startDate = data.getString("startDate"),
//                        endDate = data.getString("endDate"),
//                        isPayed = data.getBoolean("isPayed"),
//                        isExpired = data.getBoolean("isExpired")
//                    )
                } else if (responseCode == 400) {
                    Log.d("ProfileRepository", "Response Body: $responseMessage")
                    Log.e("AuthRepo", "Token Expired")
                    sendTokenToServer(idToken, db, nama, profile)
                }
                return@withContext false
            } catch (e: Exception) {
                Log.e("ProfileRepository", "Gagal membuat transaksi ", e)

                return@withContext false
            }
        }
    }

    suspend fun updateSubscription(idToken: String,id:String):Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://japritv.vercel.app/api/user/subscription/${id}")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", "Bearer $idToken")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("GetDataProfileRepository", "Response Code: $responseCode")
                Log.d("GetDataProfileRepository", "Response Body: $responseMessage")

                if (responseCode == 200) {
                    return@withContext true
                    Log.d("ProfileRepository", "Response Body: $responseMessage")
                }else{

                    Log.d("ProfileRepository", "Response Body: $responseMessage")
                }
                return@withContext false
            } catch (e: Exception) {
                return@withContext false
            }
        }
    }

    suspend fun deleteSubscription(idToken: String,id:String):Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://japritv.vercel.app/api/user/subscription/${id}")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "DELETE"
                connection.setRequestProperty("Authorization", "Bearer $idToken")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("GetDataProfileRepository", "Response Code: $responseCode")
                Log.d("GetDataProfileRepository", "Response Body: $responseMessage")

                if (responseCode == 200) {
                    return@withContext true
                    Log.d("ProfileRepository", "Response Body: $responseMessage")
                }else{

                    Log.d("ProfileRepository", "Response Body: $responseMessage")
                }
                return@withContext false
            } catch (e: Exception) {
                return@withContext false
            }
        }
    }

}
