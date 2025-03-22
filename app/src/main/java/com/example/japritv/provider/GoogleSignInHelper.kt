package com.example.japritv.provider

import android.content.Context
import android.util.Log
import com.example.japritv.model.GoogleAccount
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoogleSignInHelper(private val context: Context) {

    private val googleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("635195907142-ml4hd7eo14u3okb22lqqc6o4b0tcfhhk.apps.googleusercontent.com") // Ganti dengan WEB_CLIENT_ID Anda
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    suspend fun getGoogleAccount(): GoogleAccount? = withContext(Dispatchers.IO) {
        return@withContext try {
            val signInTask = googleSignInClient.silentSignIn()
            val account = Tasks.await(signInTask)

            account?.let {
                GoogleAccount(
                    token = it.idToken ?: "",
                    displayName = it.displayName ?: "",
                    profileImageUrl = it.photoUrl?.toString() ?: ""
                )
            }
        } catch (e: Exception) {
            Log.e("GoogleSignInHelper", "Silent sign-in failed: ${e.message}")
            null
        }
    }
}