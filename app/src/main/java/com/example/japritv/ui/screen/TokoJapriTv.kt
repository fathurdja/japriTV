package com.example.japritv.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.ui.components.profile.CoinGrid

@Composable
fun TokoJapriTV() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Koin",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
            // Menambahkan jarak antar huruf pada harga
        )
        CoinGrid()

    }
}

@Preview
@Composable
private fun TokoJapriTvPreview() {
    TokoJapriTV()
}