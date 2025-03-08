package com.example.japritv.ui.components.payment


// Necessary imports
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.japritv.R
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.japritv.ui.theme.JapriTvTheme


// Main Composable function
@Composable
fun ListPayment() {
    Card(

        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = Modifier.padding(16.dp).background(color = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Pembayaran Instan / E-Wallet",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.vector__7_),
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Payment Options
            PaymentOption(R.drawable.gopay, "GoPay")
            PaymentOption(R.drawable.gopay, "GoPay")
            PaymentOption(R.drawable.gopay, "GoPay")
            PaymentOption(R.drawable.gopay, "GoPay")
            PaymentOption(R.drawable.gopay, "GoPay")

        }
    }
}

// Composable for each payment option
@Composable
fun PaymentOption(iconRes: Int, name: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = name, fontSize = 14.sp)
    }
    HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
}

// Preview function
@Preview(showBackground = true)
@Composable
fun ListPaymentPreview() {
   JapriTvTheme {
       ListPayment()
   }
}
