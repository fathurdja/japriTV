package com.example.japritv.ui.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.PendingIntentCompat.getActivity

import androidx.credentials.CredentialManager
import com.example.japritv.R
import com.example.japritv.Repository.AuthRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.dao.AuthToken
import com.example.japritv.provider.GoogleAuthUiProvider
import com.example.japritv.ui.components.Login.ButtonLogin
import com.example.japritv.ui.components.Login.Footer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceType", "ContextCastToActivity",
    "RememberReturnType"
)
@Composable
fun LoginScreen(onClick: () -> Unit) {
    val context = LocalContext.current
    val activity = LocalContext.current as? Activity
    val credentialManager: CredentialManager = remember { CredentialManager.create(context) }
    val coroutineScope = rememberCoroutineScope()
    val googleAuthUiProvider = remember { activity?.let { GoogleAuthUiProvider(it, credentialManager) } }
    val db = remember { AppDatabase.getDatabase(context) } // Hindari pemanggilan berulang
    val authTokenDao = remember { db.authTokenDao() }
    Scaffold(
        containerColor = Color.Black,
        bottomBar = {
            Footer()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(vertical = 190.dp),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.japripay),
                    contentDescription = "Japri TV Logo",
                    modifier = Modifier.size(80.dp)


                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Japri TV",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(48.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 19.dp)
                ) {
                    Column {
                        ButtonLogin(
                            onClick = { /* Handle Japri Pay login */ },
                            text = "Login dengan Japri Pay",
                            icon = R.drawable.japripay,

                            color = Color(0xFF0033CC)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ButtonLogin(
                            onClick = { /* Handle Facebook login */ },
                            text = "Login dengan Facebook",
                            icon = R.drawable.path14,
                            color = Color(0xFF3E67B5)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ButtonLogin(
                            onClick = {
                                coroutineScope.launch {
                                    val googleAccount = googleAuthUiProvider?.signIn()
                                    if (googleAccount != null) {
                                        withContext(Dispatchers.IO) {
                                            authTokenDao.saveToken(AuthToken(token = googleAccount.token))
                                            println(googleAccount.token)
                                        }

                                        val success = withContext(Dispatchers.IO) {
                                            AuthRepository.sendTokenToServer(
                                                idToken = googleAccount.token,
                                                db = db,
                                                nama = googleAccount.displayName ?: "",
                                                profile = googleAccount.profileImageUrl ?: ""
                                            )
                                        }

                                        if (success) {
                                            onClick()
                                        } else {
                                            Log.e("LoginScreen", "Gagal mengautentikasi token di server")
                                        }
                                    } else {
                                        Log.e("LoginScreen", "Google Sign-In failed")
                                    }
                                }
                            },
                            text = "Login dengan Google",
                            icon = R.drawable.logo_googleg_48dp,
                            color = Color(0xFF313131)
                        )

                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(onClick = {})
}