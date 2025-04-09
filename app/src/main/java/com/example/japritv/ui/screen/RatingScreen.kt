package com.example.japritv.ui.screen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.japritv.R
import com.example.japritv.model.Show
import com.example.japritv.ui.components.home.CardRating
import com.example.japritv.viewmodel.VideoViewModel


@Composable
fun RatingScreen(videoViewModel: VideoViewModel,) {
    val mostViewedVideos by videoViewModel.mostViewedVideos.collectAsState()

    LaunchedEffect(Unit) {
        videoViewModel.fetchMostViewedVideos() // ✅ Kirim token agar API dapat diakses
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (!mostViewedVideos?.message.isNullOrBlank() && mostViewedVideos?.data!!.isNotEmpty()) {
                mostViewedVideos?.data.let { videoList ->
                    items(videoList!!.size) { index ->
                        val show = videoList[index]

                        if (show.isRelease) {
                            Box(
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .padding(2.dp)
                            ) {
                                CardRating(
                                    rank = index + 1,
                                    show = show,
                                    popularity = show.totalSales.toString(),
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.video_slash_icon_1),
                                contentDescription = "Not Found",
                                modifier = Modifier.fillMaxWidth(0.6f)
                            )
                            Text(
                                text = "konten tidak ditemukan",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

    }
}



