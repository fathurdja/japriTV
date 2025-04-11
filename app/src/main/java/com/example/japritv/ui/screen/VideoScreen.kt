package com.example.japritv.ui.screen

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.*
import androidx.compose.foundation.pager.VerticalPager

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.japritv.R
import com.example.japritv.model.ResponseVideo
import com.example.japritv.ui.components.video.ContainerEpisode
import com.example.japritv.ui.components.video.VideoPage
import com.example.japritv.viewmodel.VideoViewModel
import com.google.accompanist.pager.ExperimentalPagerApi


import kotlinx.coroutines.delay

@kotlin.OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable

fun VideoScreen(viewModel: VideoViewModel,navController: NavController) {
    val videoList by viewModel.dataList.collectAsState()
    val context = LocalContext.current

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { videoList.size }
    )

    var showSheet by remember { mutableStateOf(false) }
    var selectedEpisode by remember { mutableStateOf(1) }

    val sheetState = rememberModalBottomSheetState()

    Column {
        VerticalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
        ) { page ->
            if (videoList.isNotEmpty()) {
                val video = videoList[page].video.find { it.episode == selectedEpisode }
                val videoId = video?.id ?: ""
                val videoUrl = "https://tv.japrime.id/video/preview/$videoId"
                val like = video?.like

                VideoPage(
                    share = 0,
                    like = like!!,
                    judul = videoList[page].title,
                    deskripsi = "",
                    url = videoUrl,
                    onClick = { navController.navigate("nowPlaying/${video.id}") },
                    onEpisodeClick = { showSheet = true },
                    onLikeClick = {},
                    onBookmarkClick = {}
                )
            }
        }
    }

    // Bottom Sheet untuk memilih episode
    if (showSheet && pagerState.currentPage in videoList.indices) {
        ModalBottomSheet(
            containerColor = Color.Black,
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = { showSheet = false }
        ) {
            val currentVideo = videoList[pagerState.currentPage].video.size

            ContainerEpisode(
                onEpisodeSelected = { episode ->
                    selectedEpisode = episode
                    showSheet = false // ✅ Tutup sheet setelah memilih episode
                },
                selectedEpisode = selectedEpisode,
                totalEpisodes = currentVideo,
                title = videoList[pagerState.currentPage].title,
                poster = "https://tv.japrime.id/video/poster/${videoList[pagerState.currentPage].idPoster}"
            )
        }
    }
}



