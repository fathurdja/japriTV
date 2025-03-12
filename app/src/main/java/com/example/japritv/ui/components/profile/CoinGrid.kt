package com.example.japritv.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R
import com.google.common.io.Files.append

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun CoinGrid() {
    // Data yang akan ditampilkan secara dinamis
    val coinData = listOf(
        Pair("10 + 2", "Rp 19.000"),
        Pair("20", "Rp 29.000"),
        Pair("50 + 5", "Rp 89.000"),
        Pair("100 + 15", "Rp 169.000")
    )

    // LazyGrid untuk menampilkan data secara dinamis dalam grid
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Dua kolom dalam satu baris
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        items(coinData) { coin ->
            CoinCard(coin.first, coin.second)
        }
    }
}

// Composable function for individual coin card
@Composable
fun CoinCard(coins: String, price: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color(0xFF333333)),
        modifier = Modifier
            .width(180.dp)
            .height(100.dp)
            .padding(8.dp) // Padding antar kartu
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.point_solid_1),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = formatCoinText(coins),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,

                )
            }
            Text(
                text = price,
                color = Color.White,
                fontSize = 14.sp,
                letterSpacing = 1.0.sp // Menambahkan jarak antar huruf pada harga
            )
        }
    }
}

// Function untuk memformat teks coins dengan warna berbeda untuk angka dengan tanda "+"
@Composable
fun formatCoinText(coins: String): AnnotatedString {
    val parts = coins.split(" + ")
    val firstPart = parts[0]
    val secondPart = parts.getOrNull(1) ?: ""

    return buildAnnotatedString {
        append(firstPart)
        if (secondPart.isNotEmpty()) {
            withStyle(style = SpanStyle(color = Color(0xFF898888))) {
                append(" + $secondPart")
            }
        }
    }
}

// Preview function
@Preview(showBackground = true)
@Composable
fun CoinGridPreview() {
    CoinGrid()
}
