package com.example.japritv.ui.components.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R

@Composable
fun ActionButtons() {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Bookmark button
        Box(modifier = Modifier.clickable {  }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.vector__13_),
                    contentDescription = "Bookmark",
                    tint = Color.White
                )
                Text(text = "452", color = Color.White, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Like button
        Box(modifier = Modifier.clickable {  }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.vector__14_),
                    contentDescription = "Like",
                    tint = Color.White
                )
                Text(text = "1180", color = Color.White, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Episodes button
        Box(modifier = Modifier.clickable {  }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.playlist_play_icon_1), // Placeholder icon
                    contentDescription = "Episodes",
                    tint = Color.White
                )
                Text(text = "Episode", color = Color.White, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Share button
        Box(modifier = Modifier.clickable {  }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.share_vector_icon_1),
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
    ActionButtons()
}