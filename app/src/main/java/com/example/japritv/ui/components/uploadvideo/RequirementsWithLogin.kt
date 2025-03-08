package com.example.japritv.ui.components.uploadvideo

import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.example.japritv.ui.theme.JapriTvTheme

@Composable
fun RequirementsWithLogin() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp) // Padding adjusted to 12px
            .clip(RoundedCornerShape(12.dp)) // Radius adjusted to 12px
            .background(Color(0xFF2D2D2D))
            .padding(horizontal = 12.dp, vertical = 10.dp)// Dark background
           // Padding adjusted to 12px inside the Box
    ) {
        Column() {
            Text(
                text = "Sebelum Upload, Pastikan:",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp)) // Gap adjusted to 12px

            // Syarat 1: Jaminan Rp 150.000 per judul
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.money_integral_icon_1),
                    contentDescription = "Money Icon",
                    tint = Color(0xFFF8B600), // Yellow color for money icon
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp)) // Gap adjusted to 12px
                Text(
                    text = "Jaminan Rp 150.000 per judul",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp)) // Gap adjusted to 12px

            // Syarat 2: Upload akan diarahkan ke halaman khusus
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.vector__2_),
                    contentDescription = "Arrow Icon",
                    tint = Color(0xFFF8B600),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp)) // Gap adjusted to 12px
                Text(
                    text = "Upload akan diarahkan ke halaman khusus",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp)) // Gap adjusted to 12px

            // Syarat 3: Maksimal 120 episode per judul video
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.vector__4_),
                    contentDescription = "List Icon",
                    tint = Color(0xFFF8B600),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp)) // Gap adjusted to 12px
                Text(
                    text = "Maksimal 120 episode per judul video",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp)) // Gap adjusted to 12px

            // Syarat 4: File lebih dari 75 MB wajib konversi
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.vector__3_),
                    contentDescription = "Sync Icon",
                    tint = Color(0xFFF8B600),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp)) // Gap adjusted to 12px
                Text(
                    text = "File lebih dari 75 MB wajib konversi (Rp 5.000/video)",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun RequirementsWithLoginPreview() {
    JapriTvTheme {
        RequirementsWithLogin()
    }
}
