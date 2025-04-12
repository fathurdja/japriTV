package com.example.japritv.ui.components.video

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.japritv.R


@OptIn(UnstableApi::class)
@Composable
fun VideoPage(
    player: SimpleExoPlayer,
    share: Int,
    like: Int,
    judul: String,
    deskripsi: String,
    onClick: () -> Unit,
    onEpisodeClick: () -> Unit,
    onLikeClick: () -> Unit,
    onBookmarkClick: () -> Unit,
) {
    val isLiked = remember { mutableStateOf(false) }
    val isBookmarked = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                PlayerView(it).apply {
                    this.player = player
                    useController = false
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
        )

        Column(modifier = Modifier.fillMaxHeight()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(modifier = Modifier.weight(4f)) {
                        Text(
                            text = judul,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = deskripsi,
                            fontSize = 13.sp,
                            color = Color.White,
                            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.End
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconWithText(iconRes = R.drawable.vector__13_, text = "",onClick = {
                                isBookmarked.value = !isBookmarked.value
                                onBookmarkClick()
                            },
                                tint = if (isBookmarked.value) Color.Red else Color.White)
                            Spacer(modifier = Modifier.height(24.dp))
                            IconWithText(iconRes = R.drawable.vector__14_, text = if (isLiked.value)"${like + 1}" else like.toString(),  onClick = {
                                isLiked.value = !isLiked.value
                                onLikeClick()
                            },
                                tint = if (isLiked.value) Color.Red else Color.White)
                            Spacer(modifier = Modifier.height(24.dp))
                            IconWithText(iconRes = R.drawable.playlist_play_icon_1, text = "Episode", onClick = onEpisodeClick)
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(android.graphics.Color.parseColor("#200B0B")).copy(alpha = 0.8f))
                    .height(60.dp)
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                Button(
                    onClick = { onClick() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(android.graphics.Color.parseColor("#D22F26"))
                    ),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Tonton Sekarang", color = Color.White, fontSize = 12.sp, letterSpacing = 2.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Image(
                            painter = painterResource(id = R.drawable.vector__11_),
                            contentDescription = null,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IconWithText(iconRes: Int, text: String, onClick: () -> Unit, tint: Color = Color.White) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(tint)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = text, color = Color.White, fontSize = 13.sp)
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewMovieScreen() {
//    VideoPage(
//        like = 1190,
//        share = 452,
//        judul = "Money Heist: Korea. Joint Economic Area",
//        deskripsi = "Tonton keseruan 8 pencuri melakukan penyanderaan dan mengunci diri di Badan...",
//        onClick = {},
//        onEpisodeClick = {},
//        onBookmarkClick = {},
//        onLikeClick = {},
//        player = SimpleExoPlayer(LocalContext.current)
//    )
//}