package com.example.japritv.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.japritv.R
import com.example.japritv.model.Show
import com.example.japritv.viewmodel.VideoViewModel

@Composable
fun FeaturedGridSection(navController: NavController, videoViewModel: VideoViewModel) {
    val videos by videoViewModel.dataList.collectAsState()
    val listState = rememberLazyListState()
    var selectedIndex by remember { mutableStateOf(0) }

    // Observasi perubahan indeks saat LazyRow digulirkan
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                selectedIndex = index
            }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        // Featured Video Carousel
        Box(modifier = Modifier.fillMaxWidth()) {
            if (videos.isEmpty()) {
                // Menampilkan CircularProgressIndicator saat data sedang dimuat
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp),
                    color = Color.White
                )
            } else {
                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(videos) { index, video ->
                        Box(
                            modifier = Modifier
                                .width(350.dp)
                                .height(180.dp)
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.image_7), // Placeholder
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }

        // Video Title dan Indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            videos.getOrNull(selectedIndex)?.title?.let {
                Text(
                    text = it,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Page Indicator
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(videos.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (index == selectedIndex) 8.dp else 6.dp)
                            .background(
                                color = if (index == selectedIndex) Color.White else Color.Gray,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}
