package com.example.japritv.Repository

import android.util.Log
import com.example.japritv.Repository.AuthRepository.sendTokenToServer
import com.example.japritv.dao.AppDatabase
import com.example.japritv.dao.PaymentConfigEntity
import com.example.japritv.dao.PaymentDataEntity
import com.example.japritv.model.BankList

import com.example.japritv.model.PaymentData
import com.example.japritv.model.UploadVideoData
import com.example.japritv.model.UploadVideoGroupData
import com.example.japritv.model.UploadVideoResponse
import com.example.japritv.model.koinData
import com.example.japritv.model.subscriptionData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileNotFoundException
import java.net.HttpURLConnection
import java.net.URL
import kotlin.reflect.typeOf

object ProfileRepository {
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
    ): PaymentDataEntity? {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: ""
                Log.e("ProfileRepository", "Token: $token")
                val url = URL("https://tv.japrime.id/payment")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val requestBody = JSONObject().apply {
                    put("type", "subscription")
                    put("method", type)
                    put("bank", bank)
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



                val jsonResponse = JSONObject(responseMessage)
                val dataObject = jsonResponse.optJSONObject("data") ?: return@withContext null
                val userObject = dataObject.optJSONObject("id_user") ?: JSONObject()
                val invoiceObject = dataObject.optJSONObject("invoice") ?: JSONObject()
                val detailObject = dataObject.optJSONObject("detail") ?: JSONObject()

                val name = dataObject.optString("name", "")
                val type = dataObject.optString("type", "")
                val paymentData = PaymentDataEntity(
                    id = dataObject.optString("_id", ""),
                    name = name,
                    type = type,
                    status = invoiceObject.optString("status", ""),
                    createdAt = dataObject.optString("createdAt", ""),
                    updatedAt = dataObject.optString("updatedAt", ""),
                    userName = userObject.optString("name", ""),
                    bank = invoiceObject.optString("bankShortCode", ""),
                    amount = detailObject.optInt("amount", 0),
                    unique =  detailObject.optInt("unique", 0),
                    serverFee =  detailObject.optInt("server_fee", 0),
                    admin = detailObject.optInt("admin", 0),
                    totalAmount = detailObject.optInt("total_amount", 0),
                    invoiceId = invoiceObject.optString("id", ""),
                    vaNumber = invoiceObject.optString("accountNo", ""),
                    vaName = invoiceObject.optString("displayName", ""),
                    level = if (type == "subscription") detailObject.optString("level", "") else null,
                    idVideo = if (type == "video") detailObject.optString("id_video", "") else null,
                    totalEpisode = if (type == "video") detailObject.optInt("total_episode", 0) else null,
                )

                // ✅ Simpan ke Room
                db.temporaryPayment().insert(paymentData)

                return@withContext paymentData


            } catch (e: Exception) {
                Log.e("ProfileRepository", "Gagal membuat subscription", e)

                return@withContext null
            }
        }
    }
    suspend fun makeDataTransactionVideo(
        type: String,
        bank: String,
        db: AppDatabase,
        ): PaymentDataEntity? {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: ""
                val url = URL("https://tv.japrime.id/payment")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val requestBody = JSONObject().apply {
                    put("type", "video")
                    put("method", type)  // Replace with actual creatorId
                    put("bank", bank)// Replace with actual amount if needed
                }.toString()

                Log.d("ProfileRepository", "Request Body: $requestBody")
                connection.outputStream.use { outputStream ->
                    outputStream.write(requestBody.toByteArray())
                    outputStream.flush()
                }

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }

                val jsonResponse = JSONObject(responseMessage)
                val dataObject = jsonResponse.optJSONObject("data") ?: return@withContext null
                val userObject = dataObject.optJSONObject("id_user") ?: JSONObject()
                val invoiceObject = dataObject.optJSONObject("invoice") ?: JSONObject()
                val detailObject = dataObject.optJSONObject("detail") ?: JSONObject()

                val name = dataObject.optString("name", "")
                val type = dataObject.optString("type", "")
                val paymentData = PaymentDataEntity(
                    id = dataObject.optString("_id", ""),
                    name = name,
                    type = type,
                    status = invoiceObject.optString("status", ""),
                    createdAt = dataObject.optString("createdAt", ""),
                    updatedAt = dataObject.optString("updatedAt", ""),
                    userName = userObject.optString("name", ""),
                    bank = invoiceObject.optString("bankShortCode", ""),
                    amount = detailObject.optInt("amount", 0),
                    unique =  detailObject.optInt("unique", 0),
                    serverFee =  detailObject.optInt("server_fee", 0),
                    admin = detailObject.optInt("admin", 0),
                    totalAmount = detailObject.optInt("total_amount", 0),
                    invoiceId = invoiceObject.optString("id", ""),
                    vaNumber = invoiceObject.optString("accountNo", ""),
                    vaName = invoiceObject.optString("displayName", ""),
                    level = if (type == "subscription") detailObject.optString("level", "") else null,
                    idVideo = if (type == "video") detailObject.optString("id_video", "") else null,
                    totalEpisode = if (type == "video") detailObject.optInt("total_episode", 0) else null,
                )

                // ✅ Simpan ke Room
                db.temporaryPayment().insert(paymentData)

                return@withContext paymentData
            } catch (e: Exception) {
                Log.e("ProfileRepository", "Gagal membuat transaksi ", e)


                return@withContext null
            }
        }
    }

    suspend fun getDataTransaction(db: AppDatabase): PaymentDataEntity? {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: ""
                val url = URL("https://tv.japrime.id/payment/invoice")
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
                val type = dataObject.optString("type", "")
                val paymentData = PaymentDataEntity(
                    id = dataObject.optString("_id", ""),
                    name = name,
                    type = type,
                    status = invoiceObject.optString("status", ""),
                    createdAt = dataObject.optString("createdAt", ""),
                    updatedAt = dataObject.optString("updatedAt", ""),
                    userName = userObject.optString("name", ""),
                    bank = invoiceObject.optString("bankShortCode", ""),
                    amount = detailObject.optInt("amount", 0),
                    unique =  detailObject.optInt("unique", 0),
                    serverFee =  detailObject.optInt("server_fee", 0),
                    admin = detailObject.optInt("admin", 0),
                    totalAmount = detailObject.optInt("total_amount", 0),
                    invoiceId = invoiceObject.optString("id", ""),
                    vaNumber = invoiceObject.optString("accountNo", ""),
                    vaName = invoiceObject.optString("displayName", ""),
                    level = if (type == "subscription") detailObject.optString("level", "") else null,
                    idVideo = if (type == "video") detailObject.optString("id_video", "") else null,
                    totalEpisode = if (type == "video") detailObject.optInt("total_episode", 0) else null,
                )

                // ✅ Simpan ke Room
                db.temporaryPayment().insert(paymentData)

                return@withContext paymentData
            } catch (e: Exception) {
                Log.e("GetDataTransaction", "Exception: ${e.message}", e)
                return@withContext null
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
    suspend fun likeVideo(db: AppDatabase,idVideo: String): Boolean? {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: ""
                Log.d("UploadVideoRepository", "Token: $token")

                val url = URL("https://tv.japrime.id/video/like/{$idVideo}")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = false

                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("UploadVideoRepository", "Response Body: $responseMessage")
                return@withContext true


            } catch (e: FileNotFoundException) {
                Log.e("UploadVideoRepository", "Endpoint not found! Check your API URL.", e)
                null
            } catch (e: Exception) {
                Log.e("UploadVideoRepository", "Error fetching videos", e)
                null
            }
        }
    }
    suspend fun cancelPayment(db: AppDatabase,idpayment: String): Boolean? {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: ""
                Log.d("UploadVideoRepository", "Token: $token")

                val url = URL("https://tv.japrime.id/payment/invoice")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = false
                val requestBody = JSONObject().apply {
                    put("action", "CANCELED")
                    put("id_payment", idpayment)
                }.toString()
                connection.outputStream.use { outputStream ->
                    outputStream.write(requestBody.toByteArray())
                    outputStream.flush()
                }
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("payment Canceled", "Response Body: $responseMessage")
                return@withContext true


            } catch (e: FileNotFoundException) {
                Log.e("UploadVideoRepository", "Endpoint not found! Check your API URL.", e)
                null
            } catch (e: Exception) {
                Log.e("UploadVideoRepository", "Error fetching videos", e)
                null
            }
        }
    }
    suspend fun fetchAndStorePaymentConfig(db: AppDatabase): PaymentConfigEntity? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://tv.japrime.id/service")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json")

                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(responseMessage).optJSONObject("data") ?: return@withContext null

                val subscription = json.getJSONObject("subscription")
                val subPrice = subscription.getJSONObject("price")
                val subValidity = subscription.getJSONObject("validity")

                val payment = json.getJSONObject("payment")

                val paymentTypesArray = payment.getJSONArray("type")
                val paymentMethodsArray = payment.getJSONArray("method")
                val banksArray = payment.getJSONArray("bank")

                val banksJsonArray = JSONArray()
                for (i in 0 until banksArray.length()) {
                    val bank = banksArray.getJSONObject(i)
                    banksJsonArray.put(bank)
                }

                val config = PaymentConfigEntity(
                    subPriceMingguan = subPrice.optInt("mingguan", 0),
                    subPriceBulanan = subPrice.optInt("bulanan", 0),
                    subValidityMingguan = subValidity.optInt("mingguan", 0),
                    subValidityBulanan = subValidity.optInt("bulanan", 0),
                    paymentTypes = paymentTypesArray.toString(),
                    paymentMethods = paymentMethodsArray.toString(),
                    banks = banksJsonArray.toString()
                )

                db.paymentDataclass().insertConfig(config)
                config
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
    suspend fun topUpSaldo(
        type: String,
        amount: Int,
        db: AppDatabase,
        bank: String
    ): PaymentDataEntity? {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: return@withContext null

                val url = URL("https://tv.japrime.id/payment")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val requestBody = JSONObject().apply {
                    put("type", "coin")
                    put("method", type)
                    put("coin", amount)
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
                Log.d("ProfileRepository", "ini respons dari API Response Body: $responseMessage")

                val jsonResponse = JSONObject(responseMessage)

                val dataObject = jsonResponse.optJSONObject("data") ?: return@withContext null
                val userObject = dataObject.optJSONObject("id_user") ?: JSONObject()
                val invoiceObject = dataObject.optJSONObject("invoice") ?: JSONObject()
                val detailObject = dataObject.optJSONObject("detail") ?: JSONObject()
                val name = dataObject.optString("name", "")
                val type = dataObject.optString("type", "")


                val paymentData = PaymentDataEntity(
                    id = dataObject.optString("_id", ""),
                    name = name,
                    type = type,
                    status = invoiceObject.optString("status", ""),
                    createdAt = dataObject.optString("createdAt", ""),
                    updatedAt = dataObject.optString("updatedAt", ""),
                    userName = userObject.optString("name", ""),
                    bank = invoiceObject.optString("bankShortCode", ""),
                    amount = detailObject.optInt("amount", 0),
                    unique = if (type == "coin") detailObject.optInt("unique", 0) else 0,
                    serverFee = if (type == "coin") detailObject.optInt("server_fee", 0) else 0,
                    admin = detailObject.optInt("admin", 0),
                    totalAmount = detailObject.optInt("total_amount", 0),
                    invoiceId = invoiceObject.optString("id", ""),
                    vaNumber = invoiceObject.optString("accountNo", ""),
                    vaName = invoiceObject.optString("displayName", ""),
                    level = if (type == "subscription") detailObject.optString("level", "") else null,
                    idVideo = if (type == "video") detailObject.optString("id_video", "") else null,
                    totalEpisode = if (type== "video") detailObject.optInt("total_episode", 0) else null,
                )

                db.temporaryPayment().insert(paymentData)
                return@withContext paymentData

            } catch (e: Exception) {
                Log.e("ProfileRepository", "Exception during topUpSaldo", e)
                return@withContext null
            }
        }
    }
    suspend fun topUpSaldoQr(
        type: String,
        amount: Int,
        db: AppDatabase,
    ): PaymentDataEntity? {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: return@withContext null

                val url = URL("https://tv.japrime.id/payment")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val requestBody = JSONObject().apply {
                    put("type", "coin")
                    put("method", type)
                    put("coin", amount)
                }.toString()

                Log.d("ProfileRepository", "Request Body: $requestBody")

                connection.outputStream.use { outputStream ->
                    outputStream.write(requestBody.toByteArray())
                    outputStream.flush()
                }

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }

                Log.d("ProfileRepository", "Response Code: $responseCode")
                Log.d("ProfileRepository", "ini respons dari API Response Body: $responseMessage")


                return@withContext getDataTransaction(db = db)


            } catch (e: Exception) {
                Log.e("ProfileRepository", "Exception during topUpSaldo", e)
                return@withContext null
            }
        }
    }

    suspend fun topUpSaldoUser(
        type: String,
        amount: Int,
        db: AppDatabase,
        bank: String
    ): PaymentDataEntity? {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: return@withContext null

                val url = URL("https://tv.japrime.id/payment/saldo")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val requestBody = JSONObject().apply {
                    put("method", type)
                    put("coin", amount)
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
                Log.d("ProfileRepository", "ini respons dari API Response Body: $responseMessage")


                return@withContext getDataTransaction(db = db)


            } catch (e: Exception) {
                Log.e("ProfileRepository", "Exception during topUpSaldo", e)
                return@withContext null
            }
        }
    }
    suspend fun topUpSaldoUserQr(
        type: String,
        amount: Int,
        db: AppDatabase,
    ): PaymentDataEntity? {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: return@withContext null

                val url = URL("https://tv.japrime.id/payment/saldo")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val requestBody = JSONObject().apply {
                    put("method", type)
                    put("coin", amount)
                }.toString()

                Log.d("ProfileRepository", "Request Body: $requestBody")

                connection.outputStream.use { outputStream ->
                    outputStream.write(requestBody.toByteArray())
                    outputStream.flush()
                }

                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }

                Log.d("ProfileRepository", "Response Code: $responseCode")
                Log.d("ProfileRepository", "ini respons dari API Response Body: $responseMessage")


                return@withContext getDataTransaction(db = db)


            } catch (e: Exception) {
                Log.e("ProfileRepository", "Exception during topUpSaldo", e)
                return@withContext null
            }
        }
    }


    suspend fun getHistoryTransactionVideo(db: AppDatabase): List<PaymentData> {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: ""
                val url = URL("https://tv.japrime.id/payment?type=video")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")

                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("GetDataTransaction", "Raw response: $responseMessage")

                val jsonResponse = JSONObject(responseMessage)

                val dataArray = jsonResponse.optJSONArray("data") ?: return@withContext emptyList()

                val paymentList = mutableListOf<PaymentData>()

                for (i in 0 until dataArray.length()) {
                    val dataObject = dataArray.getJSONObject(i)
                    val userObject = dataObject.optJSONObject("id_user") ?: JSONObject()
                    val invoiceObject = dataObject.optJSONObject("invoice") ?: JSONObject()
                    val detailObject = dataObject.optJSONObject("detail") ?: JSONObject()
                    val name = dataObject.optString("name", "")
                    val type = dataObject.optString("type", "")

                    val paymentData = PaymentData(
                        id = dataObject.optString("_id", ""),
                        name = name,
                        type = type,
                        status = invoiceObject.optString("status", ""),
                        createdAt = dataObject.optString("createdAt", ""),
                        updatedAt = dataObject.optString("updatedAt", ""),
                        userName = userObject.optString("name", ""),
                        bank = invoiceObject.optString("bankShortCode", ""),
                        amount = detailObject.optInt("amount", 0),
                        unique = if (type == "coin") detailObject.optInt("unique", 0) else 0,
                        serverFee = if (type == "coin") detailObject.optInt("server_fee", 0) else 0,
                        admin = detailObject.optInt("admin", 0),
                        totalAmount = detailObject.optInt("total_amount", 0),
                        invoiceId = invoiceObject.optString("id", ""),
                        vaNumber = invoiceObject.optString("accountNo", ""),
                        vaName = invoiceObject.optString("displayName", ""),
                        level = if (type == "subscription") detailObject.optString("level", "") else null,
                        idVideo = if (type == "video") detailObject.optString("id_video", "") else null,
                        totalEpisode = if (type== "video") detailObject.optInt("total_episode", 0) else null,
                    )
                    paymentList.add(paymentData)
                }

                return@withContext paymentList
            } catch (e: Exception) {
                Log.e("GetDataTransaction", "Exception: ${e.message}", e)
                return@withContext emptyList()
            }
        }
    }
    suspend fun getHistoryTransactionSubs(db: AppDatabase): List<PaymentData> {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: ""
                val url = URL("https://tv.japrime.id/payment?type=subscription")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")

                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("GetDataTransaction", "Raw response: $responseMessage")

                val jsonResponse = JSONObject(responseMessage)

                val dataArray = jsonResponse.optJSONArray("data") ?: return@withContext emptyList()

                val paymentList = mutableListOf<PaymentData>()

                for (i in 0 until dataArray.length()) {
                    val dataObject = dataArray.getJSONObject(i)
                    val userObject = dataObject.optJSONObject("id_user") ?: JSONObject()
                    val invoiceObject = dataObject.optJSONObject("invoice") ?: JSONObject()
                    val detailObject = dataObject.optJSONObject("detail") ?: JSONObject()
                    val name = dataObject.optString("name", "")
                    val type = dataObject.optString("type", "")

                    val paymentData = PaymentData(
                        id = dataObject.optString("_id", ""),
                        name = name,
                        type = type,
                        status = invoiceObject.optString("status", ""),
                        createdAt = dataObject.optString("createdAt", ""),
                        updatedAt = dataObject.optString("updatedAt", ""),
                        userName = userObject.optString("name", ""),
                        bank = invoiceObject.optString("bankShortCode", ""),
                        amount = detailObject.optInt("amount", 0),
                        unique = if (type == "coin") detailObject.optInt("unique", 0) else 0,
                        serverFee = if (type == "coin") detailObject.optInt("server_fee", 0) else 0,
                        admin = detailObject.optInt("admin", 0),
                        totalAmount = detailObject.optInt("total_amount", 0),
                        invoiceId = invoiceObject.optString("id", ""),
                        vaNumber = invoiceObject.optString("accountNo", ""),
                        vaName = invoiceObject.optString("displayName", ""),
                        level = if (type == "subscription") detailObject.optString("level", "") else null,
                        idVideo = if (type == "video") detailObject.optString("id_video", "") else null,
                        totalEpisode = if (type== "video") detailObject.optInt("total_episode", 0) else null,

                    )
                    paymentList.add(paymentData)
                }

                return@withContext paymentList
            } catch (e: Exception) {
                Log.e("GetDataTransaction", "Exception: ${e.message}", e)
                return@withContext emptyList()
            }
        }
    }
    suspend fun getHistoryTransactionCoin(db: AppDatabase): List<PaymentData> {
        return withContext(Dispatchers.IO) {
            try {
                val authInfo = db.authTokenDao().getToken()
                val token = authInfo?.token ?: ""
                val url = URL("https://tv.japrime.id/payment?type=coin")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", token)
                connection.setRequestProperty("Content-Type", "application/json")

                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("GetDataTransaction", "Raw response: $responseMessage")

                val jsonResponse = JSONObject(responseMessage)

                val dataArray = jsonResponse.optJSONArray("data") ?: return@withContext emptyList()

                val paymentList = mutableListOf<PaymentData>()

                for (i in 0 until dataArray.length()) {
                    val dataObject = dataArray.getJSONObject(i)
                    val userObject = dataObject.optJSONObject("id_user") ?: JSONObject()
                    val invoiceObject = dataObject.optJSONObject("invoice") ?: JSONObject()
                    val detailObject = dataObject.optJSONObject("detail") ?: JSONObject()
                    val name = dataObject.optString("name", "")
                    val type = dataObject.optString("type", "")

                    val paymentData = PaymentData(
                        id = dataObject.optString("_id", ""),
                        name = name,
                        type = type,
                        status = invoiceObject.optString("status", ""),
                        createdAt = dataObject.optString("createdAt", ""),
                        updatedAt = dataObject.optString("updatedAt", ""),
                        userName = userObject.optString("name", ""),
                        bank = invoiceObject.optString("bankShortCode", ""),
                        amount = detailObject.optInt("amount", 0),
                        unique = if (type == "coin") detailObject.optInt("unique", 0) else 0,
                        serverFee = if (type == "coin") detailObject.optInt("server_fee", 0) else 0,
                        admin = detailObject.optInt("admin", 0),
                        totalAmount = detailObject.optInt("total_amount", 0),
                        invoiceId = invoiceObject.optString("id", ""),
                        vaNumber = invoiceObject.optString("accountNo", ""),
                        vaName = invoiceObject.optString("displayName", ""),
                        level = if (type == "subscription") detailObject.optString("level", "") else null,
                        idVideo = if (type == "video") detailObject.optString("id_video", "") else null,
                        totalEpisode = if (type== "video") detailObject.optInt("total_episode", 0) else null,
                    )
                    paymentList.add(paymentData)
                }

                return@withContext paymentList
            } catch (e: Exception) {
                Log.e("GetDataTransaction", "Exception: ${e.message}", e)
                return@withContext emptyList()
            }
        }
    }

}
