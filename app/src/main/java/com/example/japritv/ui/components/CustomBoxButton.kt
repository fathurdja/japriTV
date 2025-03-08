package com.example.japritv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun CustomBoxButton(
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(50.dp) // Set the height of the button
            .background(Color(0XFFD22F26), RoundedCornerShape(8.dp)) // Red background with rounded corners
            .clickable(onClick = onClick) // Handle click
            .padding(horizontal = 16.dp), // Padding inside the button
        contentAlignment = Alignment.Center // Center the text inside the box
    ) {
        Text(
            text = title,
            color = Color.White, // White text
            fontWeight = FontWeight.Bold, // Bold text
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
private fun CustomBoxButtonPreview() {
    JapriTvTheme {
        CustomBoxButton(title = "Click Me", onClick = {})

    }
}