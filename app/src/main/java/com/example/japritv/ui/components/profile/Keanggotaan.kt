package com.example.japritv.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Keanggotaan(tipe:String,harga:String,hargaLama:String,onClick:()->Unit,benefits:String) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .width(400.dp)
            .height(135.dp).padding(horizontal = 16.dp).clip(RoundedCornerShape(8.dp)).clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF2A2521),
                            Color(0xFF000000), // Black
                          // Dark Brown
                              // Gold Brown
                        )
                    )
                )
        )
        // Main card
        Card(
            modifier = Modifier.fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Title Box
                Text(
                    text = tipe,
                    color = Color(0xFFEFC55F),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Price Box
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = harga,
                        color = Color(0xFFEFC55F),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = hargaLama,
                        color = Color(0xFFB0B0B0),
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.LineThrough
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Button Box
                Box(
                    modifier = Modifier
                        .background(Color(0xFFC5A75A), shape = RoundedCornerShape(10.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .align(Alignment.Start)
                ) {
                    Text(
                        text = benefits,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Timer banner
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(topEnd = 8.dp, bottomStart = 8.dp))
                .background( Color(0xFFD22F26))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = "Waktu Terbatas 23:59:56",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
fun PreviewCardMinimal() {
    Keanggotaan(
        tipe = "Bulanan",
        harga = "Rp 100.000",
        hargaLama = "Rp 150.000",
        onClick = {},
        benefits = "Semua Episode Bisa dinonton Gratis"
    )
}

