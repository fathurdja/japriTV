package com.example.japritv.ui.components.uploadvideo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.japritv.R

@Composable
fun AddEpisodeButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Icon(painter = painterResource(R.drawable.plus), contentDescription = "Tambah")
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Tambah Episode", color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun AddEpisodeButtonPreview() {
    AddEpisodeButton(onClick = {})
}