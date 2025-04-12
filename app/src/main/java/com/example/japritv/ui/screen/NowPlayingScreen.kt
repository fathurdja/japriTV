package com.example.japritv.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.widget.Toast
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
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import coil.compose.rememberAsyncImagePainter
import com.example.japritv.R
import com.example.japritv.dao.AppDatabase
import com.example.japritv.model.Video
import com.example.japritv.ui.components.ContentwatchFailed
import com.example.japritv.ui.components.HeaderRightWithIcon
import com.example.japritv.ui.components.video.ActionButtons
import com.example.japritv.ui.components.video.ContainerEpisode
import com.example.japritv.ui.components.video.ModalityContainer
import com.example.japritv.viewmodel.VideoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.japritv.utils.downloadVideoToCache
import com.example.japritv.utils.shareVideo
import com.example.japritv.viewmodel.UserViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun VideoVerticalPagerScreen(
    viewModel: VideoViewModel,
    userViewModel: UserViewModel,
    Id: String,
    index: Int = 0,
    db: AppDatabase,
    login: () -> Unit,
    topUpsaldo: () -> Unit,
    onClickBack: () -> Unit
) {
    val video by viewModel.selectedVideo.collectAsState()
    val dataUser by userViewModel.userInfo.collectAsState()
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var selectedEpisode by remember { mutableStateOf(1) }
    var isSheetEnabled by remember { mutableStateOf(false) } // ✅ sheet state


    LaunchedEffect(Id) {
        viewModel.fetchVideoById(Id)
    }

    LaunchedEffect(video) {
        video?.let { viewModel.getPoster(it.groupid) }
    }

    val poster = "https://tv.japrime.id/video/poster/${video?.idPoster}"
    val context = LocalContext.current
    val pagerState = rememberPagerState(
        initialPage = index,
        initialPageOffsetFraction = 0f,
        pageCount = { video?.video?.size ?: 0 }
    )
    LaunchedEffect(selectedEpisode) {
        val targetIndex = video?.video?.indexOfFirst { it.episode == selectedEpisode } ?: 0
        pagerState.animateScrollToPage(targetIndex)
    }
    LaunchedEffect(Unit) {
        userViewModel.loadUserInfo()
        println(userViewModel.userInfo.value)
    }

    if (dataUser == null) {
        ContentwatchFailed(
            urlImage = poster ?: "",
            onClick = { login() },
            text = "Login Untuk Nonton"
        )

    } else if (dataUser!!.saldo == 0){
        ContentwatchFailed(
            urlImage = poster ?: "",
            onClick = {topUpsaldo() },
            text = "Top up Saldo Untuk Nonton"
        )
    }
    else{
        Column(modifier = Modifier.fillMaxSize()) {
            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                video?.video?.getOrNull(page)?.let { videoItem ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        VideoPlayer(
                            context = context,
                            videoUrl = "https://tv.japrime.id/video/watch/${videoItem.id}",
                            thumbnailUrl = poster ?: "",
                            pagerState = pagerState,
                            db = db,
                            title = video!!.title,
                            episode = videoItem.episode,
                            groupId = video!!.groupid,
                            posterId = video!!.idPoster,
                            onLikeClick = { viewModel.likeVideo(db = db, idVideo = videoItem.id) },
                            onStartShare = {
                                isSheetEnabled = true
                            },
                            onFinishShare = {
                                isSheetEnabled = true
                            },
                            onVideoStarted = { idVideo, title, episode, idGroup, idPoster ->
                                // Simpan riwayat video yang ditonton
                                viewModel.saveToHistory(
                                    videoId = idVideo,
                                    title = title,
                                    episode = episode,
                                    idgroup = idGroup,
                                    idPoster = idPoster
                                )
                            },
                            onClickEpisode = { showSheet = true},
                            likes = videoItem.like,
                        )

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
                                onBackClick = { onClickBack() }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            containerColor = Color.Black,
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = { showSheet = false }
        ) {
            if (video == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                video?.video?.let { episodes ->
                    ContainerEpisode(
                        onEpisodeSelected = { episode ->
                            selectedEpisode = episode
                            showSheet = false
                        },
                        selectedEpisode = selectedEpisode,
                        totalEpisodes = episodes.size,
                        title = video!!.title,
                        poster = poster
                    )
                }

            }
        }
    }
}


@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoPlayer(
    videoUrl: String,
    thumbnailUrl: String,
    context: Context,
    pagerState: PagerState,
    db: AppDatabase,
    title: String,
    episode: Int,
    groupId: String,
    posterId: String,
    onVideoStarted: (String, String, Int, String, String) -> Unit,
    onClickEpisode: () -> Unit,
    onLikeClick: () -> Unit,
    likes: Int,
    onStartShare: () -> Unit,
    onFinishShare: () -> Unit
) {
    val image = rememberAsyncImagePainter(model = thumbnailUrl)
    var isPlaying by remember { mutableStateOf(true) }
    var isBuffering by remember { mutableStateOf(true) }
    var isVideoReady by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }
    var showControls by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    var exoPlayer by remember { mutableStateOf<ExoPlayer?>(null) }

    // Init player
    LaunchedEffect(videoUrl) {
        val token = db.authTokenDao().getToken()?.token ?: return@LaunchedEffect

        exoPlayer?.run {
            stop()
            release()
        }

        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
            .setDefaultRequestProperties(mapOf("Authorization" to token))

        val player = ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(httpDataSourceFactory))
            .build().apply {
                val mediaItem = MediaItem.Builder()
                    .setUri(videoUrl)
                    .setMimeType(MimeTypes.VIDEO_MP4)
                    .build()
                setMediaItem(mediaItem)
                prepare()
                playWhenReady = true
                volume = 1f
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        isBuffering = state == Player.STATE_BUFFERING || state == Player.STATE_IDLE
                        isVideoReady = state == Player.STATE_READY
                    }
                })
            }

        exoPlayer = player
        isPlaying = true
        onVideoStarted(
            videoUrl.substringAfterLast("/"), // idVideo
            title,                            // judul video
            episode,                          // episode
            groupId,                          // idGroup
            posterId                          // posterId
        )
    }

    // Release on dispose
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer?.run {
                stop()
                release()
            }
        }
    }

    // Track progress
    LaunchedEffect(exoPlayer) {
        while (true) {
            delay(1000)
            exoPlayer?.let { player ->
                if (player.duration > 0) {
                    progress = (player.currentPosition.toFloat() / player.duration.toFloat()).coerceIn(0f, 1f)
                }
            }
        }
    }

    // Auto-hide controls
    LaunchedEffect(isPlaying, pagerState.currentPage) {
        if (isPlaying) {
            delay(1500)
            showControls = false
        }
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()

            .clickable {
                exoPlayer?.let {
                    if (it.isPlaying) {
                        it.pause()
                        isPlaying = false
                    } else {
                        it.play()
                        isPlaying = true
                    }
                }
                showControls = true
            }
    ) {
        // Thumbnail hanya saat buffering
        if (isBuffering) {
            Image(
                painter = image,
                contentDescription = "Thumbnail",
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .zIndex(0f)
            )
        }

        // PlayerView hanya tampil jika video siap
        if (isVideoReady) {
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        this.player = exoPlayer
                        useController = false
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f)
            )
        }

        // Kontrol Play/Pause
        if (showControls) {
            IconButton(
                onClick = {
                    exoPlayer?.let {
                        if (it.isPlaying) {
                            it.pause()
                            isPlaying = false
                        } else {
                            it.play()
                            isPlaying = true
                        }
                    }
                },
                modifier = Modifier.align(Alignment.Center).zIndex(2f)
            ) {
                Icon(
                    painter = painterResource(id = if (exoPlayer?.isPlaying == true) R.drawable.pause_circle else R.drawable.play_circle),
                    contentDescription = if (exoPlayer?.isPlaying == true) "Pause" else "Play",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 180.dp, start = 16.dp, end = 16.dp)
                .zIndex(2f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            ActionButtons(
                onBookmarkClick = { /* TODO */ },
                onLikeClick = {  onLikeClick() },
                onEpisodesClick = { onClickEpisode() },
                onShareClick = {
                    coroutineScope.launch {
                        onStartShare()
                        Toast.makeText(context, "Video sedang diunduh...", Toast.LENGTH_SHORT).show()

                        val token = db.authTokenDao().getToken()?.token ?: return@launch
                        val videoId = videoUrl.substringAfterLast("/")
                        val file = downloadVideoToCache(context, videoId, token)

                        if (file != null) {
                            shareVideo(context, file)
                            Toast.makeText(context, "Video berhasil dibagikan", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Gagal mengunduh video", Toast.LENGTH_SHORT).show()
                        }

                        onFinishShare()
                    }
                },
                jumlahLike = likes
            )
        }

        // Bottom info
        Column(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .zIndex(2f)
                .clickable { onClickEpisode() }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.playlist_play_icon_1),
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("EP.${pagerState.currentPage + 1}", color = Color.White)
                }
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = "Next",
                    tint = Color.White
                )
            }

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                color = Color.White,
                trackColor = Color.LightGray
            )
        }
    }
}


