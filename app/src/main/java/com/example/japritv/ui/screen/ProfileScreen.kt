package com.example.japritv.ui.screen


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.japritv.dao.AppDatabase
import com.example.japritv.ui.components.profile.MembershipCard

import com.example.japritv.ui.components.profile.MenuProfile
import com.example.japritv.ui.components.profile.UserInfo
import com.example.japritv.ui.components.profile.UserProfile
import com.example.japritv.ui.components.profile.Wallet
import com.example.japritv.ui.theme.JapriTvTheme
import com.example.japritv.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(navController: NavController, db: AppDatabase) {
    val viewModel: UserViewModel = remember { UserViewModel(db) }
    val userInfo by viewModel.userInfo.collectAsState()
    val subscriptionInfo by viewModel.subscriptionInfo.collectAsState()

    LaunchedEffect(userInfo) {
        userInfo?.let { user ->
            Log.e("profile", "$user")
            viewModel.loadSubscriptionInfo (db)
        }
    }
    fun formatDate(isoDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val date: Date? = inputFormat.parse(isoDate)
        return date?.let { outputFormat.format(it) } ?: "Invalid Date"
    }

    // Background hitam untuk tampilan profil
    Scaffold(
        containerColor = Color.Black
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                if (userInfo != null) {
                    UserProfile(
                        nameUser = userInfo?.name
                            ?.split(" ") // Pisah berdasarkan spasi
                            ?.take(2)    // Ambil 2 kata pertama
                            ?.joinToString(" ") // Gabungkan kembali
                            ?: "Pengunjung",

                        email = userInfo?.email ?: "Email tidak tersedia",
                        picture = userInfo?.urlPicture ?: "",
                        userId = userInfo?.userId ?: "ID tidak tersedia",
                        onClick = { navController.navigate("login") }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if (subscriptionInfo != null && subscriptionInfo?.isPayed == true) {
                        val date = formatDate(subscriptionInfo!!.endDate)
                        MembershipCard(
                            level = subscriptionInfo!!.level,
                            endDate = date,
                            totalVideosWatched = 0,
                            totalVideosAvailable = 0
                        )
                    }

                } else {
                    UserInfo(
                        nameUser = "Pengunjung",
                        profileImageUrl = null,
                        onLoginClick = { navController.navigate("login") },
                        onCopyClick = { /* Handle Copy ID */ }
                    )
                }



                Spacer(modifier = Modifier.height(16.dp))

                // Komponen Dompet
                Wallet(
                    onIsiUlangClick = { navController.navigate("TokoJapri") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Tambahan menu lainnya (contoh)
                MenuProfile(navController)

            }
        }
    }
}




