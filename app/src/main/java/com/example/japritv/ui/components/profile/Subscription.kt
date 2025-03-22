package com.example.japritv.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MembershipCard(level: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFF2A2521),
                shape = RoundedCornerShape(16.dp)
            )

            .border(
                width = 1.dp,
                color = Color(0xFFC5A75A),
                shape = RoundedCornerShape(16.dp)
            )

    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color(0xFF413327),
                    shape = RoundedCornerShape(8.dp)
                ).align(Alignment.TopEnd)
                .padding(top = 12.dp, end = 20.dp)

        ) {
            Text(
                text = "Masa berlaku 21/07/2025",

                color = Color(0xFFC5A75A),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth() .border(
                width = 1.dp,
                color = Color(0xFFC5A75A),
                shape = RoundedCornerShape(16.dp)
            ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.padding(top = 24.dp, start = 12.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.Bottom,) {
                Text(
                    text = "Anggota ${level}",

                    color =Color(0xFFC5A75A),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Kamu sudah menonton 37 dari 50 video.",
                    color = Color(0xFFC5A75A),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }


        }
    }
}

// Preview function
@Preview()
@Composable
fun PreviewMinimalCard() {
    MembershipCard(level = "Mingguan")
}