package com.example.japritv.Repository

import android.content.Context
import android.credentials.CredentialManager
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.example.japritv.provider.GoogleAuthUiProvider
import com.example.japritv.provider.GoogleSignInHelper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class AuthRepositoryNew(
    private val googleSignInHelper: GoogleSignInHelper,
    private val client: OkHttpClient,
    private val tokenManager: TokenManager
) {

    suspend fun loginWithGoogle(): Boolean {
        return try {
            // 1. Ambil token Google dari GoogleSignInHelper
            val googleAccount = googleSignInHelper.getGoogleAccount()
                ?: throw Exception("Google sign-in failed")

            val googleToken = googleAccount.token
            if (googleToken.isEmpty()) throw Exception("Google token is empty")

            // 2. Kirim token Google ke API /auth/google
            val request = Request.Builder()
                .url("https://japritv.vercel.app/auth/google")
                .get()
                .addHeader("Authorization", "Bearer $googleToken")
                .build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                // 3. Simpan token API untuk transaksi berikutnya
                val jsonResponse = JSONObject(response.body?.string() ?: "{}")
                val message = jsonResponse.getString("message")
                val apiToken = googleToken
                val expiresIn = 3600L

                tokenManager.saveToken(apiToken, expiresIn)
                Log.e("pesan",message)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login failed: ${e.message}")
            false
        }
    }

    suspend fun getValidToken(): String {
        if (tokenManager.isTokenExpired()) {
//            refreshAuthToken() // Refresh token jika expired
        }
        return tokenManager.getToken() ?: throw Exception("Token not available")
    }

     suspend fun refreshAuthToken(token:String) {
        try {

            // 2. Kirim token Google ke API /auth/google
            val request = Request.Builder()
                .url("https://japritv.vercel.app/api/auth/google")
                .get()
                .addHeader("Authorization", "Bearer $token")
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val jsonResponse = JSONObject(response.body?.string() ?: "{}")
                val message = jsonResponse.getString("message")
                val apiToken = token
                val expiresIn = 3600L

                tokenManager.saveToken(apiToken, expiresIn)
                Log.e("pesan",message)
            } else {
                throw Exception("Failed to refresh token")
            }
        } catch (e: Exception) {
            throw Exception("Error refreshing token: ${e.message}")
        }
    }
}
