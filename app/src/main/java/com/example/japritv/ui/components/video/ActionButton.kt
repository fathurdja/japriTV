package com.example.japritv.ui.components.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R

@Composable
fun ActionButtons(
    jumlahLike: Int,
    onBookmarkClick: () -> Unit = {},
    onLikeClick: () -> Unit = {},
    onEpisodesClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    var isLiked by remember { mutableStateOf(false) }
    var isBookmarked by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Bookmark button
        Box(modifier = Modifier.clickable {
            isBookmarked = !isBookmarked
            onBookmarkClick() }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.vector__13_),
                    modifier = Modifier.size(30.dp),
                    contentDescription = "Bookmark",
                    tint = if (isBookmarked) Color.Red else
                        Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Like button
        Box(modifier = Modifier.clickable {
            isLiked = !isLiked // toggle
            onLikeClick()
        }) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.vector__14_),
                    modifier = Modifier.size(30.dp),
                    contentDescription = "Like",
                    tint = if (isLiked) Color.Red else
                    Color.White
                )
                Text(text = if (isLiked) (jumlahLike + 1).toString() else jumlahLike.toString(), color = Color.White, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Episodes button
        Box(modifier = Modifier.clickable { onEpisodesClick() }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.playlist_play_icon_1),
                    modifier = Modifier.size(30.dp),// Placeholder icon
                    contentDescription = "Episodes",
                    tint = Color.White
                )
                Text(text = "Episode", color = Color.White, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Share button
        Box(modifier = Modifier.clickable { onShareClick() }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.share_vector_icon_1),
                    modifier = Modifier.size(30.dp),
                    contentDescription = "Share",
                    tint = Color.White
                )
                Text(text = "Bagikan", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}

@Preview
@Composable
private fun ActionButtonsPreview() {
    ActionButtons(
        jumlahLike = 12,
    )
}