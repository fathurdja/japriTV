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
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.japritv.R
import com.example.japritv.dao.AppDatabase
import com.example.japritv.model.ResponseVideo
import com.example.japritv.ui.components.video.ContainerEpisode
import com.example.japritv.ui.components.video.VideoPage
import com.example.japritv.utils.VideoCache
import com.example.japritv.viewmodel.VideoViewModel
import com.google.accompanist.pager.ExperimentalPagerApi


import kotlinx.coroutines.delay

@OptIn(UnstableApi::class)
@kotlin.OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun VideoScreen(viewModel: VideoViewModel,onClick: (String) -> Unit,db: AppDatabase) {
    val videoList by viewModel.dataList.collectAsState()
    val context = LocalContext.current
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { videoList.size })
    var showSheet by remember { mutableStateOf(false) }
    var selectedEpisode by remember { mutableStateOf(1) }
    val sheetState = rememberModalBottomSheetState()
    val playerMap = remember { mutableMapOf<Int, SimpleExoPlayer>() }
    Column {
        VerticalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ) { page ->
            if (videoList.isNotEmpty()) {
                val isCurrentPage = pagerState.currentPage == page
                val idGroup = videoList[page].groupid
                val video = videoList[page].video.find { it.episode == selectedEpisode }
                val videoId = video?.id ?: ""
                val videoUrl = "https://tv.japrime.id/video/preview/$videoId"

                val player = remember(page) {
                    val httpDataSourceFactory = DefaultHttpDataSource.Factory()
                        .setAllowCrossProtocolRedirects(true)

                    val cacheDataSourceFactory = CacheDataSource.Factory()
                        .setCache(VideoCache.getInstance(context))
                        .setUpstreamDataSourceFactory(httpDataSourceFactory)
                        .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

                    val mediaSourceFactory = DefaultMediaSourceFactory(cacheDataSourceFactory)

                    SimpleExoPlayer.Builder(context)
                        .setMediaSourceFactory(mediaSourceFactory)
                        .build().also {
                            playerMap[page] = it
                        }
                }


                LaunchedEffect(isCurrentPage, videoUrl) {
                    val mediaItem = MediaItem.Builder()
                        .setUri(Uri.parse(videoUrl))
                        .setMimeType(MimeTypes.APPLICATION_MP4)
                        .build()

                    player.setMediaItem(mediaItem)
                    player.prepare()
                    player.playWhenReady = isCurrentPage

                    if (!isCurrentPage) {
                        player.pause()
                    }
                }

                DisposableEffect(Unit) {
                    onDispose {
                        player.release()
                        playerMap.remove(page)
                    }
                }

                VideoPage(
                    player = player,
                    share = 0,
                    like = video?.like ?: 0,
                    judul = videoList[page].title,
                    deskripsi = "",
                    onClick = { onClick(idGroup) },
                    onEpisodeClick = { showSheet = true },
                    onLikeClick = {
                        video?.let {
                            viewModel.likeVideo(db = db, idVideo = it.id)
                        }
                    },
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



