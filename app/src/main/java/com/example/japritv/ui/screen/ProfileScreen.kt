package com.example.japritv.ui.screen



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.japritv.ui.components.profile.Group214
import com.example.japritv.ui.components.profile.UserInfo
import com.example.japritv.ui.theme.JapriTvTheme

@Composable
fun ProfileScreen() {
    // Background hitam untuk tampilan profil
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column {
            // Komponen UserInfo
            UserInfo(
                onLoginClick = { /* Handle Login Click */ },
                onCopyClick = { /* Handle Copy ID */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Komponen Dompet
            Group214(
                onIsiUlangClick = { /* Handle Isi Ulang */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tambahan menu lainnya (contoh)
            MenuList()
        }
    }
}

@Composable
fun MenuList() {
    val menuItems = listOf(
        "Riwayat Pembelian",
        "Layanan Pelanggan",
        "Pengaturan",
        "Tentang Kami",
        "Pengaturan Bahasa",
        "Kebijakan Privasi",
        "Marketing Plan",
        "Bersihkan Cache"
    )

    Column {
        menuItems.forEach { menu ->
            Text(
                text = menu,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    JapriTvTheme {
        ProfileScreen()
    }
}
