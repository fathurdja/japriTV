package com.example.japritv.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R


@Composable
fun RiwayatCard(movieName: String, date: String,harga: String, image: Int,status: String,onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF333333))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFB8860B), shape = CircleShape)

            ){
                Image(modifier = Modifier.align(Alignment.Center), painter = painterResource(image), contentDescription = null)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Column {
                    Text(
                        text = movieName,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = date,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }

        }

        Box (
        ){
            Column {
               Row {
                   Icon(
                       painter = painterResource(id = R.drawable.check_bullet),
                       contentDescription = null,
                       tint =  when {

                           status == "paid" -> Color.Green
                           status == "pending" -> Color.Yellow
                           status == "expired" -> Color.Red
                           else -> Color.Yellow
                       },
                   )
                   Spacer(modifier = Modifier.width(4.dp))
                   Text(
                       text =
                       when {
                           status == "paid" -> "Paid"
                           status == "pending" -> "Pending"
                           status == "expired" -> "Expired"
                           else -> "Pending"
                       },
                       color = Color.Gray,
                       fontSize = 14.sp
                   )
               }
                Text(
                    text = harga,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewUserInfoCard() {
    RiwayatCard(movieName = "movie", date = "21 Mei 2025, 12:15 PM ", harga = "Rp.100,000", image = R.drawable.theaters, status = "pending", onClick = {},
    )
}