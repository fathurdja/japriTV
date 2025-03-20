package com.example.japritv.provider

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential

import com.example.japritv.R
import com.example.japritv.model.GoogleAccount

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential


class GoogleAuthUiProvider(
    private val activityContext: Context,
    private val credentialManager: CredentialManager
) {
    suspend fun signIn(): GoogleAccount? = try {
        val credential = credentialManager.getCredential(
            context = activityContext,
            request = getCredentialRequest()
        ).credential
        handleSignIn(credential)
    } catch (e: Exception) {
        Log.e("GoogleAuthUiProvider", "Sign-in failed: ${e.message}")
        null
    }

    private fun handleSignIn(credential: androidx.credentials.Credential): GoogleAccount? = when {
        credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
            try {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                GoogleAccount(
                    token = googleIdTokenCredential.idToken,
                    displayName = googleIdTokenCredential.displayName ?: "",
                    profileImageUrl = googleIdTokenCredential.profilePictureUri?.toString()
                )
            } catch (e: com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException) {
                Log.e("GoogleAuthUiProvider", "Token parsing failed: ${e.message}")
                null
            }
        }
        else -> {
            Log.e("GoogleAuthUiProvider", "Unexpected credential type")
            null
        }
    }

    private fun getCredentialRequest(): androidx.credentials.GetCredentialRequest = androidx.credentials.GetCredentialRequest.Builder()
        .addCredentialOption(getGoogleIdOption())
        .build()

    private fun getGoogleIdOption(): com.google.android.libraries.identity.googleid.GetGoogleIdOption = com.google.android.libraries.identity.googleid.GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setAutoSelectEnabled(true)
        .setServerClientId("635195907142-ml4hd7eo14u3okb22lqqc6o4b0tcfhhk.apps.googleusercontent.com") // ganti dengan WEB_CLIENT_ID
        .build()
}