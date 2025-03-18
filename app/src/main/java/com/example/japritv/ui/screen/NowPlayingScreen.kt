package com.example.japritv.ui.screen

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.rememberAsyncImagePainter

import com.example.japritv.R
import com.example.japritv.ui.components.Header
import com.example.japritv.ui.components.HeaderRightWithIcon
import com.example.japritv.ui.components.video.ActionButtons
import com.example.japritv.ui.theme.JapriTvTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun VideoVerticalPagerScreen() {
    val context = LocalContext.current
    val videoItems = listOf(
        VideoItem(
            title = "Money Heist",
            episode = 1,
            url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4",
            thumbnail = "https://i.ytimg.com/vi/xjS6PtR5Q6Q/hqdefault.jpg"
        ),
        VideoItem(
            title = "Money Heist",
            episode = 2,
            url = "https://www.w3schools.com/html/mov_bbb.mp4",
            thumbnail = "https://i.ytimg.com/vi/aqz-KE-bpKQ/hqdefault.jpg"
        ),
        VideoItem(
            title = "Money Heist",
            episode = 3,
            url = "https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_1mb.mp4",
            thumbnail = "https://i.ytimg.com/vi/aqz-KE-bpKQ/hqdefault.jpg"
        )
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { videoItems.size }
    )
    Column(modifier = Modifier.fillMaxSize()) {

        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val videoItem = videoItems[page]
            Box(modifier = Modifier.padding(vertical = 8.dp)) {
                HeaderRightWithIcon(
                    title = "${videoItem.title} Eps ${videoItem.episode}",
                    color = Color.Transparent,
                    textColor = Color.White,
                    resId = R.drawable.arrowwhite,
                    onBackClick = {}
                )
                VideoPlayer(
                    context = context,
                    videoUrl = videoItems[page].url,
                    thumbnailUrl = videoItems[page].thumbnail
                )
            }
        }
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(videoUrl: String, thumbnailUrl: String, context: Context) {
    val image = rememberAsyncImagePainter(model = thumbnailUrl)
    var isPlaying by remember { mutableStateOf(false) }
    var isBuffering by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }

    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(Uri.parse(videoUrl)))
            prepare()
            volume = 1f
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    isBuffering = state == Player.STATE_BUFFERING || state == Player.STATE_IDLE
                }

                override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                    isPlaying = isPlayingNow
                }
            })
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    LaunchedEffect(exoPlayer) {
        while (true) {
            delay(1000) // Update every 1 second
            if (exoPlayer.duration > 0) {
                progress = (exoPlayer.currentPosition.toFloat() / exoPlayer.duration.toFloat()).coerceIn(0f, 1f)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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

        // Thumbnail (Menutupi seluruh layar sebelum video mulai diputar)
        if (!isPlaying && !isBuffering) {
            Image(
                painter = image,
                contentDescription = "Thumbnail",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            )
        }

        // Pause Button
        if (isPlaying) {
            IconButton(
                onClick = { exoPlayer.pause() },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Pause", tint = Color.White, modifier = Modifier.size(48.dp))
            }
        } else if (!isBuffering) {
            IconButton(
                onClick = { exoPlayer.play() },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White, modifier = Modifier.size(48.dp))
            }
        }

        // ActionButtons di tengah sisi kanan
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 180.dp,
                    start = 16.dp,
                    end = 16.dp,
                  // Tambahkan padding bottom di sini
                ),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            ActionButtons()
        }

        // Bottom UI Elements
        Column(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 10.dp,)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("EP.1/EP.71", color = Color.White)
                     // Add spacing between text and icon

                }
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next", tint = Color.White)
            }
            LinearProgressIndicator(
                progress = {
                    progress // Dynamic progress
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                color = Color.White,
            )
        }


    }
}

data class VideoItem(
    val title: String,
    val episode: Int,
    val url: String,
    val thumbnail: String
)

@Preview
@Composable
private fun VideoVerticalPagerScreenPreview() {
    JapriTvTheme {}
}