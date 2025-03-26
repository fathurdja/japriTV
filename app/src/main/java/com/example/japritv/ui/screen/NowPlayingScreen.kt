

package com.example.japritv.ui.screen

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
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
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import coil.compose.rememberAsyncImagePainter
import com.example.japritv.R
import com.example.japritv.ui.components.HeaderRightWithIcon
import com.example.japritv.ui.components.video.ActionButtons
import com.example.japritv.ui.components.video.ContainerEpisode
import com.example.japritv.ui.components.video.ModalityContainer
import com.example.japritv.viewmodel.VideoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun VideoVerticalPagerScreen(viewModel: VideoViewModel, userId: String, onClickBack: () -> Unit) {
    val video by viewModel.selectedVideo.collectAsState()
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var selectedEpisode by remember { mutableStateOf(1) }
    LaunchedEffect(userId) {
        viewModel.fetchVideoById(userId)

    }
    LaunchedEffect(video) {
        video?.let { viewModel.getPoster(it.id) }
    }

    val poster by viewModel.poster.collectAsState()
    val context = LocalContext.current

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { video?.video?.size ?: 0 }
    )
    LaunchedEffect(selectedEpisode) {
        val targetIndex = video?.video?.indexOfFirst { it.episode == selectedEpisode } ?: 0
        pagerState.animateScrollToPage(targetIndex)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            video?.video?.getOrNull(page)?.let { videoItem ->
                Box(modifier = Modifier.fillMaxSize()) {
                    VideoPlayer(
                        context = context,
                        videoUrl = videoItem.url,
                        thumbnailUrl = poster ?: "",
                        pagerState = pagerState,
                        onClickEpisode = {showSheet=true}

                    )

                    // Header di bagian atas
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopStart)
                            .zIndex(1f)
                    ) {
                        HeaderRightWithIcon(
                            title = "${video!!.title} Episode ${videoItem.episode}",
                            color = Color.Transparent,
                            textColor = Color.White,
                            resId = R.drawable.arrowwhite,
                            onBackClick = {onClickBack()}
                        )
                    }
                }
            }
        }
    }
    if (showSheet){
        ModalBottomSheet(
            containerColor = Color.Black,
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = { showSheet = false },

            ) {
            video?.totalEpisode?.let {
                ContainerEpisode(
                    onEpisodeSelected = { episode ->
                        selectedEpisode = episode
                        showSheet = false // ✅ Tutup sheet setelah memilih episode
                    },
                    selectedEpisode = selectedEpisode,
                    totalEpisodes = it,
                    title = video!!.title,
                    poster = poster ?: ""
                )
            }



            // Tambahkan jarak bawah untuk swipe-to-dismiss


        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoPlayer(
    videoUrl: String,
    thumbnailUrl: String,
    context: Context,
    pagerState: PagerState
    ,onClickEpisode: () -> Unit
) {
    val image = rememberAsyncImagePainter(model = thumbnailUrl)
    var isPlaying by remember { mutableStateOf(true) }
    var isBuffering by remember { mutableStateOf(true) }
    var progress by remember { mutableFloatStateOf(0f) }
    var showControls by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(context)) // Tambahkan ini
            .build().apply {
                val mediaItem = MediaItem.Builder()
                    .setUri(videoUrl)
                    .setMimeType(MimeTypes.APPLICATION_M3U8) // ✅ Pastikan MIME type sesuai
                    .build()

                setMediaItem(mediaItem)
                prepare()
                playWhenReady = true
                volume = 1f
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        isBuffering = state == Player.STATE_BUFFERING || state == Player.STATE_IDLE
                    }
                })
            }
    }


    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    LaunchedEffect(isPlaying, pagerState.currentPage) {
        println("LaunchedEffect triggered: isPlaying=$isPlaying, currentPage=${pagerState.currentPage}")
        if (isPlaying) {
            delay(1000)
            showControls = false
        }
    }

    LaunchedEffect(exoPlayer) {
        while (true) {
            delay(1000)
            if (exoPlayer.duration > 0) {
                progress = (exoPlayer.currentPosition.toFloat() / exoPlayer.duration.toFloat()).coerceIn(0f, 1f)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                    isPlaying = false
                } else {
                    exoPlayer.play()
                    isPlaying = true
                }
                showControls = true
             }
    ) {

        if (!isPlaying || isBuffering) {
            Image(
                painter = image,
                contentDescription = "Thumbnail",
                modifier = Modifier.fillMaxSize()
            )
        }

        // Video Player
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Thumbnail sebelum video mulai
        if (showControls) {
            IconButton(
                onClick = {
                    if (exoPlayer.isPlaying) {
                        exoPlayer.pause()
                        isPlaying = false
                    } else {
                        exoPlayer.play()
                        isPlaying = true
                    }
                },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(
                    painter = painterResource(id = if (exoPlayer.isPlaying) R.drawable.pause_circle else R.drawable.play_circle),
                    contentDescription = if (exoPlayer.isPlaying) "Pause" else "Play",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        // ActionButtons di tengah sisi kanan
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 180.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            ActionButtons(
                onBookmarkClick = { /* Handle bookmark click */ },
                onLikeClick = { /* Handle like click */ },
                onEpisodesClick = { onClickEpisode()},
                onShareClick = { /* Handle share click */ }
            )
        }

        // Bottom UI Elements
        Column(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clickable { onClickEpisode() }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.playlist_play_icon_1), contentDescription = "Menu", tint = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("EP.1/EP.71", color = Color.White)
                }
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next", tint = Color.White)
            }

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                color = Color.White,
            )
        }
    }
}
