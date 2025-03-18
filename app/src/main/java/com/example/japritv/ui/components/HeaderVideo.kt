package com.example.japritv.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
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
fun HeaderVideo(title: String, color: Color, textColor: Color, resId:Int, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color) // Set background color
            .padding(vertical = 16.dp, horizontal = 16.dp) // Padding untuk jarak dari tepi
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // Align items vertically in the center
            modifier = Modifier.align(Alignment.CenterStart) // Align Row to the start of the Box
        ) {
            Image(
                painter = painterResource(id =resId),
                contentDescription = "close", // Set icon color to textColor
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { onBackClick() }
                    .size(15.dp)
                // Space between icon and text
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                color = textColor, // Text color
                fontSize = 20.sp, // Font size
                fontWeight = FontWeight.Bold // Bold font
            )
        }
    }
}


@Preview
@Composable
private fun HeaderVideoPreview() {
    HeaderVideo(
        title = "Header Title",
        color = Color.Transparent,
        textColor = Color.Black,
        resId = R.drawable.arrowwhite,
        onBackClick = {}
    )
}