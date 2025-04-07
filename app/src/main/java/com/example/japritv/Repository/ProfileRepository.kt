package com.example.japritv.Repository

import android.util.Log
import com.example.japritv.Repository.AuthRepository.sendTokenToServer
import com.example.japritv.dao.AppDatabase

import com.example.japritv.model.PaymentData
import com.example.japritv.model.UploadVideoData
import com.example.japritv.model.UploadVideoResponse
import com.example.japritv.model.koinData
import com.example.japritv.model.subscriptionData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.FileNotFoundException
import java.net.HttpURLConnection
import java.net.URL

object ProfileRepository {
    suspend fun updateDataSubscription(
        id: String,

        db: AppDatabase,

        ): subscriptionData? {
        return withContext(Dispatchers.IO) {
            try {

                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token
                val url = URL("https://japritv-v2.vercel.app/api/subscription/$id")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doInput = true

                val responseCode = connection.responseCode

                val responseMessage = try {
                    connection.inputStream.bufferedReader().use { it.readText() }
                } catch (e: Exception) {
                    connection.errorStream?.bufferedReader()?.use { it.readText() }
                        ?: "No response body"
                }

                Log.d("GetDataProfileRepository", "Response Code: $responseCode")
                Log.d("GetDataProfileRepository", "Response Body: $responseMessage")

                if (responseCode == 200) {
                    val jsonResponse = JSONObject(responseMessage)
                    val data = jsonResponse.optJSONObject("message") ?: return@withContext null
                    Log.e("Profile Repository", "$data")

                } else if (responseCode == 400) {
                    Log.e("AuthRepo", "Token Expired: $responseMessage")

                } else {
                    Log.e(
                        "ProfileRepository",
                        "Unexpected Response: $responseCode - $responseMessage"
                    )
                }

                return@withContext null
            } catch (e: Exception) {
                Log.e("ProfileRepository", "Error dalam getDataSubscription", e)
                return@withContext null
            }
        }
    }

    suspend fun getDataSubscription(
        db: AppDatabase,
    ): subscriptionData? {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: ""
                val url = URL("https://japritv-v2.vercel.app/api/subscription/")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", token)
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
                        userId = data.getString("user"),
                        level = data.getString("level"),
                        startDate = data.getString("startDate"),
                        endDate = data.getString("endDate"),
                        isPayed = data.getBoolean("isPayed"),
                        isExpired = data.getBoolean("isExpired"),
                        price = data.getInt("price")
                    )
                } else if (responseCode == 400) {
                    Log.e("AuthRepo", "Token Expired")

                }
                return@withContext null
            } catch (e: Exception) {
                Log.e("ProfileRepository", "Error dalam getDataSubscription", e)
                return@withContext null
            }
        }
    }

    suspend fun makeSubscription(
        level: String,
        db: AppDatabase,
        type: String,
        bank: String
    ): PaymentData? {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: ""
                Log.e("ProfileRepository", "Token: $token")
//                sendTokenToServer(idToken, db, nama, profile)
                val url = URL("https://tv.japrime.id/payment/subscription")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val requestBody = JSONObject().apply {
                    put("type", type)
                    put("level", level)
                    put("bank", bank)
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

                val jsonResponse = JSONObject(responseMessage)
                val success = jsonResponse.optBoolean("success", false)


                return@withContext if (success) {
                    getDataTransaction(db)
                } else {
                    Log.e(
                        "ProfileRepository",
                        "Top-up failed: ${jsonResponse.optString("message")}"
                    )
                    null
                }
            } catch (e: Exception) {
                Log.e("ProfileRepository", "Gagal membuat subscription", e)

                return@withContext null
            }
        }
    }

    suspend fun makeDataTransactionVideo(
        type: String,
        idVideo: String,
        bank: String,
        db: AppDatabase,
        ): PaymentData? {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: ""
                val url = URL("https://tv.japrime.id/payment/video")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val requestBody = JSONObject().apply {
                    put("type", type)  // Replace with actual creatorId
                    put("id_video", idVideo)
                    put("bank", bank)// Replace with actual amount if needed
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
                        db = db
                    )
                } else if (responseCode == 400) {
                    Log.d("ProfileRepository", "Response Body: $responseMessage")
                    Log.e("AuthRepo", "Token Expired")

                } else {
                    Log.e("ProfileRepository", "Request failed with error: $responseMessage")

                }
                return@withContext null
            } catch (e: Exception) {
                Log.e("ProfileRepository", "Gagal membuat transaksi ", e)


                return@withContext null
            }
        }
    }

    suspend fun getDataTransaction(db: AppDatabase): PaymentData? {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: ""
                val url = URL("https://tv.japrime.id/payment")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")

                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("GetDataTransaction", "Raw response: $responseMessage")

                val jsonResponse = JSONObject(responseMessage)
                val dataObject = jsonResponse.optJSONObject("data") ?: return@withContext null
                val userObject = dataObject.optJSONObject("id_user") ?: JSONObject()
                val invoiceObject = dataObject.optJSONObject("invoice") ?: JSONObject()
                val detailObject = dataObject.optJSONObject("detail") ?: JSONObject()

                val name = dataObject.optString("name", "")

                return@withContext PaymentData(
                    id = dataObject.optString("_id", ""),
                    name = name,
                    type = dataObject.optString("type", ""),
                    status = dataObject.optString("status", ""),
                    createdAt = dataObject.optString("createdAt", ""),
                    updatedAt = dataObject.optString("updatedAt", ""),
                    userName = userObject.optString("name", ""),
                    bank = detailObject.optString("bank", ""),
                    amount = detailObject.optInt("amount", 0),
                    unique = if (name == "coin") detailObject.optInt("unique", 0) else 0,
                    serverFee = if (name == "coin") detailObject.optInt("server_fee", 0) else 0,
                    admin = detailObject.optInt("admin", 0),
                    totalAmount = detailObject.optInt("total_amount", 0),
                    invoiceId = invoiceObject.optString("id", ""),
                    vaNumber = invoiceObject.optString("va_number", ""),
                    vaName = invoiceObject.optString("va_name", ""),
                    level = if (name == "subscription") detailObject.optString("level", "") else null,
                    idVideo = if (name == "video") detailObject.optString("id_video", "") else null,
                    totalEpisode = if (name == "video") detailObject.optInt("total_episode", 0) else null
                )
            } catch (e: Exception) {
                Log.e("GetDataTransaction", "Exception: ${e.message}", e)
                return@withContext null
            }
        }
    }



    suspend fun updateSubscription(idToken: String, id: String): Boolean {
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
                } else {

                    Log.d("ProfileRepository", "Response Body: $responseMessage")
                }
                return@withContext false
            } catch (e: Exception) {
                return@withContext false
            }
        }
    }

    suspend fun deleteSubscription(id: String, db: AppDatabase): Boolean {
        return withContext(Dispatchers.IO) {
            try {

                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: ""

                println(token)
                val url = URL("https://japritv.vercel.app/api/user/subscription/${id}")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "DELETE"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("GetDataProfileRepository", "Response Code: $responseCode")
                Log.d("GetDataProfileRepository", "Response Body: $responseMessage")

                if (responseCode == 200) {
                    return@withContext true
                    Log.d("ProfileRepository", "Response Body: $responseMessage")
                } else {

                    Log.d("ProfileRepository", "Response Body: $responseMessage")
                }
                return@withContext false
            } catch (e: Exception) {
                return@withContext false
            }
        }
    }

    suspend fun getVideoUploaded(db: AppDatabase): List<UploadVideoData> {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: ""
                Log.d("UploadVideoRepository", "Token: $token")

                val url = URL("https://tv.japrime.id/creator/upload")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = false

                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("UploadVideoRepository", "Response Body: $responseMessage")

                val gson = Gson()
                val videoResponse = gson.fromJson(responseMessage, UploadVideoResponse::class.java)

                return@withContext if (videoResponse.success) {
                    videoResponse.data.filter { !it.release }
                } else {
                    Log.e("UploadVideoRepository", "API returned success = false")
                    emptyList()
                }
            } catch (e: FileNotFoundException) {
                Log.e("UploadVideoRepository", "Endpoint not found! Check your API URL.", e)
                emptyList()
            } catch (e: Exception) {
                Log.e("UploadVideoRepository", "Error fetching videos", e)
                emptyList()
            }
        }
    }


    suspend fun updateTransaction(db: AppDatabase, id: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: ""
                Log.d("UploadVideoRepository", "Token: $token")
                val url = URL("https://japritv-v2.vercel.app/api/transaction/${id}")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true// karena ini GET request

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }

                Log.d("UploadVideoRepository", "Response Code: $responseCode")
                Log.d("UploadVideoRepository", "Response Body: $responseMessage")

                if (responseCode == 200) {
                    return@withContext true
                } else {
                    Log.e("UploadVideoRepository", "Failed pay video : $responseMessage")
                    return@withContext false
                }
            } catch (e: FileNotFoundException) {
                Log.e("UploadVideoRepository", "Endpoint not found! Check your API URL.", e)
                return@withContext false
            } catch (e: Exception) {
                Log.e("UploadVideoRepository", "Error pay video", e)
                return@withContext false
            }
        }
    }

    suspend fun topUpSaldo(
        type: String,
        amount: Int,
        db: AppDatabase,
        bank: String
    ): PaymentData? {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: return@withContext null

                val url = URL("https://tv.japrime.id/payment/coin")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val requestBody = JSONObject().apply {
                    put("type", type)
                    put("amount", amount)
                    put("bank", bank)
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

                val jsonResponse = JSONObject(responseMessage)
                val success = jsonResponse.optBoolean("success", false)

                return@withContext if (success) {
                    getDataTransaction(db)
                } else {
                    Log.e(
                        "ProfileRepository",
                        "Top-up failed: ${jsonResponse.optString("message")}"
                    )
                    null
                }
            } catch (e: Exception) {
                Log.e("ProfileRepository", "Exception during topUpSaldo", e)
                return@withContext null
            }
        }
    }


}
