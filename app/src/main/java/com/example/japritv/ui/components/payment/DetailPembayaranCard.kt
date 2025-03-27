package com.example.japritv.ui.components.payment

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.japritv.R
import com.example.japritv.ui.theme.JapriTvTheme

@Composable
fun DetailPembayaranCard(jumlahEps:Int,biayaPerEpisode:Int) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))

    ) {
        Column {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp), // Semua sudut rounded agar menyatu
                elevation = CardDefaults.cardElevation(2.dp),
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF5F5F5)) // Warna abu muda
                            .padding(horizontal = 16.dp, vertical = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.transaction_details_solid_1),
                            contentDescription = "Payment Icon",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Detail Pembayaran",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    // Isi Konten

                }
            }

            Card(modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp), // Semua sudut rounded agar menyatu
                elevation = CardDefaults.cardElevation(2.dp),) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total episode di upload", color = Color.Gray, fontSize = 14.sp)
                        Text("${jumlahEps} episode", color = Color.Gray, fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Biaya per episode", color = Color.Gray, fontSize = 14.sp)
                        Text("Rp $biayaPerEpisode", color = Color.Gray, fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    DashedDivider(color = Color.LightGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total pembayaran", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Rp ${jumlahEps  * biayaPerEpisode}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}


// Custom dashed divider composable
@Composable
fun DashedDivider(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    thickness: Dp = 1.dp,
    dashLength: Dp = 4.dp,
    gapLength: Dp = 4.dp
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness)
    ) {
        val width = size.width
        val dashLengthPx = dashLength.toPx()
        val gapLengthPx = gapLength.toPx()
        val totalLength = dashLengthPx + gapLengthPx
        val numberOfDashes = (width / totalLength).toInt()

        for (i in 0 until numberOfDashes) {
            val startX = i * totalLength
            drawLine(
                color = color,
                start = Offset(startX, 0f),
                end = Offset(startX + dashLengthPx, 0f),
                strokeWidth = thickness.toPx()
            )
        }
    }
}

@Preview
@Composable
private fun DetailPembayaranCardPreview() {
    JapriTvTheme {
        DetailPembayaranCard(jumlahEps = 2, biayaPerEpisode = 150000)
    }
}