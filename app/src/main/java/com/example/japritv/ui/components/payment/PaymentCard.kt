package com.example.japritv.ui.components.payment

import android.text.Layout.Alignment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R

@Composable
fun PaymentCard() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD32F2F), shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .padding(16.dp),
            //contentAlignment = Alignment.ALIGN_CENTER
        ) {
                Row(
                    //verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.group),
                        contentDescription = "Time Icon",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Selesaikan pembayaran dalam 59:50",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            PaymentInfoRow(label = "Nomor Virtual Account", value = "2370027689")
            PaymentInfoRow(label = "Nominal pembelian", value = "Rp 100.000")

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "BCA - Virtual Account", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = "PT. Japri Pay Nusantara", fontSize = 12.sp)
        }
    }
}

@Composable
fun PaymentInfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
        Row(
            modifier = Modifier.fillMaxWidth(),
            //verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Box(modifier = Modifier
                .background(Color.Gray, RoundedCornerShape(10.dp))
                .padding(horizontal = 15.dp, vertical = 5.dp)
                .clickable { onCopyClick() }) {
                Text(text = "Salin", color = Color.White)
            }
        }
    }
}

fun onCopyClick() {
    TODO("Not yet implemented")
}

@Preview
@Composable
fun PreviewPaymentCard() {
    PaymentCard()
}
