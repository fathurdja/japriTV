package com.example.japritv.ui.screen

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.example.japritv.R
import com.example.japritv.viewmodel.VideoViewModel
import kotlinx.coroutines.delay

@OptIn(UnstableApi::class)
@Composable
fun VideoScreen(viewModel: VideoViewModel) {
    val context = LocalContext.current
    val video by viewModel.currentVideo.collectAsState()

    var isBuffering by remember { mutableStateOf(true) } // ✅ Mulai dengan true karena video masih loading
    var isPlaying by remember { mutableStateOf(true) }
    var showPauseIcon by remember { mutableStateOf(false) }

    video?.let { videoData ->
        val exoPlayer = remember {
            SimpleExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(Uri.parse(videoData.videoUrl)))
                prepare()
                playWhenReady = videoData.isPlaying
                volume = 1f
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        isBuffering = state == Player.STATE_BUFFERING || state == Player.STATE_IDLE
                    }

                    override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                        isPlaying = isPlayingNow
                        if (isPlayingNow) {
                            showPauseIcon = false
                        }
                    }
                })
            }
        }

        DisposableEffect(Unit) {
            exoPlayer.playWhenReady = true
            onDispose { exoPlayer.release() }
        }

        LaunchedEffect(showPauseIcon) {
            if (showPauseIcon) {
                delay(2000) // Hilangkan ikon setelah 2 detik
                showPauseIcon = false
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    exoPlayer.playWhenReady = !exoPlayer.playWhenReady
                    showPauseIcon = true
                }
        ) {
            AndroidView(
                factory = {
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = false
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            // ✅ Circular Progress Indicator ketika buffering
            AnimatedVisibility(
                visible = isBuffering,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.Center)
                    .zIndex(2f)
            ) {
                CircularProgressIndicator(color = Color.White)
            }

            // ✅ Ikon Pause (muncul saat di-klik atau saat video di-pause)
            AnimatedVisibility(
                visible = showPauseIcon || !isPlaying,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.Center)
                    .zIndex(2f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.play_circle),
                    contentDescription = "Pause Icon",
                    tint = Color.White,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No Video Available", color = Color.White)
        }
    }
}


