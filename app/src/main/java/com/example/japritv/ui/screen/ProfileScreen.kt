package com.example.japritv.ui.screen


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.japritv.ui.components.profile.Group214
import com.example.japritv.ui.components.profile.MenuProfile
import com.example.japritv.ui.components.profile.UserInfo
import com.example.japritv.ui.components.profile.UserProfile
import com.example.japritv.ui.theme.JapriTvTheme
import com.example.japritv.viewmodel.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(navController: NavController, db: AppDatabase) {
    val viewModel: UserViewModel = remember { UserViewModel(db) }
    val userInfo by viewModel.userInfo.collectAsState()

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

                if (userInfo == null) {
                    UserInfo(
                        nameUser = "Pengunjung",
                        profileImageUrl = null,
                        onLoginClick = { navController.navigate("login") },
                        onCopyClick = { /* Handle Copy ID */ }
                    )
                }
                UserProfile(
                    nameUser = userInfo?.name
                        ?.split(" ") // Pisah berdasarkan spasi
                        ?.take(2)    // Ambil 2 kata pertama
                        ?.joinToString(" ") // Gabungkan kembali
                        ?: "Pengunjung",

                    email = userInfo?.email ?: "Email tidak tersedia",
                    picture = userInfo?.urlPicture ?: ""
                )


                Spacer(modifier = Modifier.height(16.dp))

                // Komponen Dompet
                Group214(
                    onIsiUlangClick = { navController.navigate("TokoJapri") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Tambahan menu lainnya (contoh)
                MenuProfile(navController)

            }
        }
    }
}




