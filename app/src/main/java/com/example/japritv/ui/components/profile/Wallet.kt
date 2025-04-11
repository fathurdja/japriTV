package com.example.japritv.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R


@Composable
fun Wallet(saldo:Int,onIsiUlangClick: () -> Unit) {
    val DoradoApprox100 = Color(0xFF565656) // Warna border dan garis pemisah
    val MineShaftApprox100 = Color(0xFF343434)
    // Container utama dengan border
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                Color(0xFF565656),
                RoundedCornerShape(8.dp)
            ) // Border dengan warna #565656
            .background(Color(0xFF343434), RoundedCornerShape(10.dp))

    ) {
        Column {
            // Judul Dompet
            Box(modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                        Text(
                            text = "Dompet",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )



                    Image(
                        painter = painterResource(id = R.drawable.vector__11_),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            // Garis pembatas sepanjang box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFF565656)) // Warna garis pemisah #565656
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Bagian isi (Saldo + Tombol Isi Ulang)
            Box(modifier = Modifier.padding(vertical = 3.dp, horizontal = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Saldo
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(Color.Transparent)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.point_solid_1),
                                contentDescription = null,
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = saldo.toString(),
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }

                    // Tombol Isi Ulang dengan Box
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFD32F2F), RoundedCornerShape(25.dp))
                            .clickable { onIsiUlangClick() }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Isi Ulang",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewGroup214() {
    Wallet(onIsiUlangClick = {}, saldo = 0)
}

