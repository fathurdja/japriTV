package com.example.japritv.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R
import com.example.japritv.ui.theme.JapriTvTheme

@Composable
fun UpComingCard(
    title: String,
    Video: Int,
    Poster: Int,
    description: String,
    time: String,
    onReminderClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().background(Color.Black)) {
        // Box untuk gambar video dengan rounded corners
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = Video),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().size(200.dp)
            )
            // Durasi video di pojok kanan bawah
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(4.dp)
            ) {
                Text(text = time, color = Color.White, fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        // Box untuk informasi dan poster
        Row() {
            Image(
                painter = painterResource(id = Poster),
                contentDescription = null,
                modifier = Modifier.size(90.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Column {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    color = Color.Gray,
                    fontSize = 11.sp,
                    maxLines = 2,
                    lineHeight = 14.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(5.dp))
               ButtonCustom(onClick = {}, icon = R.drawable.bell, text = "Ingatkan Saya")
            }
        }
    }
}

@Preview
@Composable
private fun UpComingCardPreview() {
    JapriTvTheme {
        UpComingCard(
            title = "Money Heist: Korea. Joint Economic Area",
            description = "Bercerita tentang permainan bertahan hidup yang mematikan. Serial ini mengisahkan tentang 456 orang yang berpartisipasi dalam permainan untuk memenangkan hadiah uang tunai.",
            Video = R.drawable.image_7,
            Poster = R.drawable.title_card,
            time = "01:47" ,
            onReminderClick = {}
        )
    }
}
