package com.example.japritv.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun Keanggotaan(tipe: String, harga: String, hargaLama: String, benefits: String, isSelected: Boolean, setharga:Boolean,onClick: () -> Unit,setHarga:()->Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .width(400.dp)
            .height(135.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .border(2.dp, if (isSelected) Color.Red else Color.Transparent, RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF2A2521),
                            Color(0xFF000000) // Black
                        )
                    )
                )
        )
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
                Text(
                    text = tipe,
                    color = Color(0xFFEFC55F),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
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

    }
}



//@Preview
//@Composable
//fun PreviewCardMinimal() {
//
//    Keanggotaan(
//        tipe = "Bulanan",
//        harga = "Rp 250.000",
//        hargaLama = "Rp 500.000",
//        benefits = "Semua episode bisa ditonton gratis",
//        isSelected = selectedMembership == "Bulanan",
//        onClick = { onSelect("Bulanan") }
//    )
//}

