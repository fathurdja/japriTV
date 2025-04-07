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
fun DetailPembayaranSubsOrCoin(color: Color, tipeSubs: String,totalPembayaran:Int, Amount: Int,typePayment:String,jumlahKoin:String,biayaTambahan:Int) {
    var isExpanded by remember { mutableStateOf(true) }
    val formatter = NumberFormat.getInstance(Locale("in", "ID"))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFE6E6E8), RoundedCornerShape(8.dp))
            .background(color)// Add border here
            .padding(12.dp)

    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }, // Toggle expansion
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

        // Show Details if expanded
        if (isExpanded) {
            Column(modifier = Modifier.padding(vertical = 25.dp)
                , horizontalAlignment = Alignment.End,) {
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
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (typePayment == "Subscription"){
                            Text(
                                text = "Anggota $tipeSubs",
                                color = Color.Gray
                            )
                            Text(
                                text = "Rp. ${formatter.format(Amount)}",
                                color = Color.Gray
                            )
                        }
                        else if (typePayment == "coin"){
                        Text(
                            text = "$jumlahKoin Koin",
                            color = Color.Gray
                        )
                        Text(
                            text = "Rp. ${formatter.format(Amount)}",
                            color = Color.Gray
                        )
                        }
                        else{
                        Text(
                            text = "Anggota $tipeSubs",
                            color = Color.Gray
                        )
                        Text(
                            text ="Rp. ${formatter.format(Amount)}",
                            color = Color.Gray
                        )
                    }
                    }
                }


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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Pajak & biaya Lainnya",
                            color = Color.Gray
                        )
                        Text(
                            text = "Rp. ${formatter.format(biayaTambahan)}",
                            color = Color.Gray
                        )
                    }
                }

                Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp))
                // Total Payment
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Nominal Pembayaran",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Rp. ${formatter.format(totalPembayaran)}",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }


    }
}