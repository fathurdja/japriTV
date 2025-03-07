package com.example.japritv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.ui.theme.JapriTvTheme

@Composable
fun Header(title: String ,color: Color,textColor:Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color) // Set background color to black
            .padding(top = 16.dp) // Add padding around the text
    ) {
        Text(
            text = title,
            color = textColor, // Text color is white
            fontSize = 20.sp, // Set font size for the text
            fontWeight = FontWeight.Bold, // Make the font bold
            modifier = Modifier.align(Alignment.Center) // Center the text in the Box
        )
    }
}

@Preview
@Composable
private fun HeaderPreview() {
    JapriTvTheme {
        Header(title = "Header Title", color = Color.Black, textColor = Color.White)

    }
}