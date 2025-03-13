package com.example.japritv.ui.screen



import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.japritv.ui.components.profile.Group214
import com.example.japritv.ui.components.profile.MenuProfile
import com.example.japritv.ui.components.profile.UserInfo
import com.example.japritv.ui.theme.JapriTvTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(navController: NavController) {
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
               // Komponen UserInfo
               UserInfo(
                   onLoginClick = { navController.navigate("login")},
                   onCopyClick = { /* Handle Copy ID */ }
               )

               Spacer(modifier = Modifier.height(16.dp))

               // Komponen Dompet
               Group214(
                   onIsiUlangClick = { navController.navigate("TokoJapri")}
               )

               Spacer(modifier = Modifier.height(16.dp))

               // Tambahan menu lainnya (contoh)
               MenuProfile(navController)

           }
       }
   }
}



@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    JapriTvTheme {
        val navController = rememberNavController()
        ProfileScreen(navController)
    }
}
