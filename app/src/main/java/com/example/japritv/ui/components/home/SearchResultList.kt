package com.example.japritv.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.japritv.R
import com.example.japritv.model.Show
import com.example.japritv.viewmodel.VideoViewModel

@Composable
fun SearchResultList(videoViewModel: VideoViewModel) {
    val mostSearchVideos by videoViewModel.mostViewedVideos.collectAsState()
    LaunchedEffect(Unit) {
        videoViewModel.fetchMostSearchVideos()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        // Menggunakan items untuk menampilkan list, pastikan ada key yang unik
        mostSearchVideos?.data?.let { videoList ->
            items(videoList.size) { index ->
                val show = videoList[index] // ✅ Ambil data dengan aman

                if (show != null) { // ✅ Pastikan show tidak null sebelum digunakan
                    Box(
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .padding(2.dp)
                    ) {
                        CardRating(
                            rank = index+1,
                            show = show,
                            popularity = show.totalSales.toString(),

                            )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } ?: item {
            Box(
                modifier = Modifier
                    .fillMaxSize(), // 🔥 Memastikan Box mengambil seluruh ukuran layar
                contentAlignment = Alignment.Center // 🔥 Menengahkan kontennya
            ) {
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.video_slash_icon_1),
                        contentDescription = "Not Found",
                        modifier = Modifier.fillMaxWidth(0.6f) // 🔥 Atur ukuran gambar agar lebih proporsional
                    )
                    Text(text = "konten tidak ditemukan", color = Color.White,)
                }

            }
        }
    }
}

