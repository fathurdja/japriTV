package com.example.japritv.ui.components.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DetailPembayaranSubsOrCoin(
    color: Color,
    tipeSubs: String,
    totalEpisode: Int,
    totalPembayaran: Int,
    biayaPerEpisode: Int,
    Amount: Int,
    typePayment: String,
    jumlahKoin: String,
    biayaTambahan: Int
) {
    var isExpanded by remember { mutableStateOf(true) }
    val formatter = NumberFormat.getInstance(Locale("in", "ID"))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFE6E6E8), RoundedCornerShape(8.dp))
            .background(color)
            .padding(12.dp)
    ) {
        Column {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Detail Pembayaran",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Toggle Details"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isExpanded) {
                Column(
                    modifier = Modifier.padding(vertical = 15.dp),
                    horizontalAlignment = Alignment.End
                ) {

                    // Rincian Utama
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.theaters),
                            tint = Color.Gray,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(24.dp),
                            contentDescription = null
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            when (typePayment) {
                                "Subscription" -> {
                                    Text("Anggota $tipeSubs", color = Color.Gray)
                                    Text("Rp. ${formatter.format(Amount)}", color = Color.Gray)
                                }

                                "coin" -> {
                                    Text("$jumlahKoin Koin", color = Color.Gray)
                                    Text("Rp. ${formatter.format(Amount)}", color = Color.Gray)
                                }

                                else -> {
                                    Text("Total Episode di Upload $totalEpisode", color = Color.Gray)
                                    Text("$totalEpisode", color = Color.Gray)
                                }
                            }
                        }
                    }

                    // Biaya & Pajak
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.iconamoon_discount),
                            tint = Color.Gray,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(24.dp),
                            contentDescription = null
                        )

                        Column(modifier = Modifier.fillMaxWidth()) {
                            if (typePayment == "video") {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Biaya per Episode", color = Color.Gray)
                                    Text("Rp. ${formatter.format(biayaPerEpisode)}", color = Color.Gray)
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Pajak & biaya Lainnya", color = Color.Gray)
                                Text("Rp. $biayaTambahan", color = Color.Gray)
                            }
                        }
                    }

                    // Garis pemisah
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    )

                    // Total
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Nominal Pembayaran", fontWeight = FontWeight.Bold)
                        Text("Rp. ${formatter.format(totalPembayaran)}", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
