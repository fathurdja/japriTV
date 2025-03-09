package com.example.japritv.ui.components.uploadvideo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R
import com.example.japritv.ui.theme.JapriTvTheme

@Composable
fun WarningUpload(text:String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp, vertical = 15.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFFFF4E6)) // Light yellow background
            .border(1.dp, Color(0xFFFFA500), RoundedCornerShape(8.dp)) // Orange border
            .padding(5.dp)
    ) {
        Row(modifier = Modifier.padding(horizontal = 7.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
            // Icon exclamation mark (warning icon)
            Icon(
                painter = painterResource(R.drawable.vector),
                contentDescription = "Warning Icon",
                tint = Color(0xFFFFA500), // Orange color for warning icon
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
            // Text for warning message
            Text(
                text = text,
                color = Color.Black,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WarningUploadPreview() {
    JapriTvTheme {
        WarningUpload("Pastikan sudah sesuai sebelum melanjutkan")
    }
}
