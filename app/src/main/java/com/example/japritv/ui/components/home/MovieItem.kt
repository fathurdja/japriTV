package com.example.japritv.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.japritv.R
import com.example.japritv.dao.VideoData


import com.example.japritv.model.Show
import com.example.japritv.model.Video

@Composable
fun MovieItem(show:VideoData, text: String,onClick: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
                .clickable { onClick(show.groupid) }
        ) {
            val painter = if (show.idPoster.isNotEmpty()) {
                rememberAsyncImagePainter(model = "https://tv.japrime.id/video/poster/${show.idPoster}") // ✅ Pakai poster dari show
            } else {
                painterResource(id = R.drawable.title_card) // 🔄 Pakai default kalau null
            }


            Image(
                painter = painter,
                contentDescription = show.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )


        }

        Spacer(modifier = Modifier.height(4.dp)) // Tambahkan jarak antara gambar dan teks

        Text(
            text = show.title ?: "Unknown",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}