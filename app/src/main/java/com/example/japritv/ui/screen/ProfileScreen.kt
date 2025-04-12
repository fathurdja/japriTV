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
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.log10
import kotlin.math.pow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(navController: NavController, db: AppDatabase) {
    val viewModel: UserViewModel = remember { UserViewModel(db) }
    val userInfo by viewModel.userInfo.collectAsState()
    val subscription = userInfo?.subscriptionLevel

    fun humanReadableByteCount(bytes: Long, si: Boolean = true): String {
        val unit = if (si) 1000 else 1024
        if (bytes < unit) return "$bytes B"
        val exp = (log10(bytes.toDouble()) / log10(unit.toDouble())).toInt()
        val pre = (if (si) "kMGTPE" else "KMGTPE")[exp - 1] + if (si) "" else "i"
        return String.format("%.1f %sB", bytes / unit.toDouble().pow(exp.toDouble()), pre)
    }
    fun getCacheSize(): String {
        val cacheDir: File = db.openHelper.writableDatabase.path.let { File(it).parentFile ?: File("") }
        var sizeInBytes = 0L

        fun calculateSize(file: File) {
            if (file.isFile) {
                sizeInBytes += file.length()
            } else {
                file.listFiles()?.forEach { calculateSize(it) }
            }
        }

        val cache = File(cacheDir, "cache")
        if (cache.exists()) {
            calculateSize(cache)
        }

        return humanReadableByteCount(sizeInBytes)
    }
    LaunchedEffect(Unit) {
        viewModel.loadUserInfo()
        Log.d("CACHE", "Ukuran cache saat ini: ${getCacheSize()}")
    }

    fun formatDate(rawDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date: Date = inputFormat.parse(rawDate)!!
            outputFormat.format(date)
        } catch (e: Exception) {
            Log.e("formatDate", "Error parsing date: ${e.message}")
            "Invalid Date"
        }
    }


    val date = userInfo?.subscriptionEndDate?.let { formatDate(it) }
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
                    if (userInfo?.subscriptionExpired == false) {
                        MembershipCard(
                            level = subscription!!,
                            endDate = date!!,
                            totalVideosWatched = 0,
                            totalVideosAvailable = 0
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Wallet(
                        onIsiUlangClick = { navController.navigate("TokoJapri") },
                        saldo = userInfo!!.saldo
                    )

                } else {
                    UserInfo(
                        nameUser = "Pengunjung",
                        profileImageUrl = null,
                        onLoginClick = { navController.navigate("login") },
                        onCopyClick = { /* Handle Copy ID */ }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Wallet(
                        onIsiUlangClick = { navController.navigate("TokoJapri") },
                        saldo = 0
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Tambahan menu lainnya (contoh)
                MenuProfile(navController)

            }
        }
    }


}




