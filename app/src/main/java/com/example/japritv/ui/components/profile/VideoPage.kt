package com.example.japritv.ui.components.profile

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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R


@Composable
fun MovieScreen(share:Int,like:Int,judul:String,deskripsi:String) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.gambarvideo), // Ganti dengan ID gambar yang sesuai
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column (
            modifier = Modifier.fillMaxHeight(),
        ){
        // Overlay Details
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
                Column (
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
                ) {
                    Column (
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                    IconWithText(iconRes = R.drawable.vector__13_, text = share.toString())
                    Spacer(modifier = Modifier.height(24.dp))
                    IconWithText(iconRes = R.drawable.vector__14_, text = like.toString())
                    Spacer(modifier = Modifier.height(24.dp))
                    IconWithText(iconRes = R.drawable.playlist_play_icon_1, text = "Episode")
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
//                modifier = Modifier.fillMaxHeight()
//                    .padding(16.dp).height(30.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(android.graphics.Color.parseColor("#D22F26"))),
                shape = RoundedCornerShape(20.dp),
            ) {
                Row (
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(text = "Tonton Sekarang", color = Color.White, fontSize = 12.sp)
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
fun IconWithText(iconRes: Int, text: String) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {}
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = text, color = Color.White, fontSize = 13.sp )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieScreen() {
    MovieScreen(like = 1190, share = 452, judul = "Money Heist: Korea. Joint Economic Area", deskripsi = "Tonton keseruan 8 pencuri melakukan penyanderaan dan mengunci diri di Badan..." )
}