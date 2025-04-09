package com.example.japritv.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.japritv.R
import com.example.japritv.model.Show
import androidx.navigation.NavController
import com.example.japritv.ui.components.home.FeaturedGridSection
import com.example.japritv.ui.components.home.MovieItem
import com.example.japritv.ui.components.home.ShowsGridSection
import com.example.japritv.ui.components.home.WidgetPlayer

import com.example.japritv.viewmodel.ShowItemViewModel
import com.example.japritv.viewmodel.VideoViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: ShowItemViewModel = viewModel(),
    videoViewModel: VideoViewModel
) {
    val shows by remember { mutableStateOf(homeViewModel.shows) }
    val movies by videoViewModel.dataList.collectAsState()

    val showPlayerWidget = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        videoViewModel.fetchVideos() // ✅ Fetch video saat layar dibuka
    }

    Scaffold { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)) {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                // Featured Show ikut scroll ke atas
                item {
                    FeaturedGridSection(navController, videoViewModel,)
                }
                // ShowsGridSection dibatasi tingginya agar tidak infinite
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(600.dp) // Sesuaikan tinggi sesuai kebutuhan
                    ) {
                        ShowsGridSection(movies,onClick = {Id ->
                            navController.navigate("nowPlaying/$Id")  })
                    }
                }
            }

            AnimatedVisibility(
                visible = showPlayerWidget.value,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                WidgetPlayer(
                    text = "Money Heist: Korea. Joint Econ...",
                    onClose = { showPlayerWidget.value = false }
                )
            }
        }
    }


}


//@Preview(showBackground = true)
//@Composable
//private fun MainScreenPreview() {
//    JapriTvTheme {
//        MainScreen()
//    }
//}