package com.example.japritv.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.japritv.R
import com.example.japritv.model.Show
import com.example.japritv.model.Video
import com.example.japritv.model.VideoDataApi

@Composable
fun CardRating(
    rank: Int,
    show: VideoDataApi, // ✅ Gunakan `VideoDataApi` langsung, hapus `Show`
    popularity: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF333333), shape = RoundedCornerShape(8.dp))
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 🔥 Nomor Ranking
        Text(
            text = rank.toString(),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 8.dp)
        )

        // 🎥 Gambar Poster (Gunakan Coil untuk URL)
        AsyncImage(
            model = "https://tv.japrime.id/video/poster/${show.idposter}", // ✅ Ambil dari API
            contentDescription = show.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp, 80.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(8.dp))

        // 📌 Informasi Video
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = show.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🏷 Genre Tags
//            if (!show.genres.isNullOrEmpty()) { // ✅ Pastikan genre tidak kosong
//                Row {
//                    show.genres.forEach { genre ->
//                        Box(
//                            modifier = Modifier
//                                .background(Color.Gray, shape = RoundedCornerShape(22.dp))
//                                .padding(horizontal = 10.dp, vertical = 2.dp)
//                        ) {
//                            Text(
//                                text = genre,
//                                color = Color.White,
//                                fontSize = 12.sp
//                            )
//                        }
//                        Spacer(modifier = Modifier.width(4.dp))
//                    }
//                }
//            }
        }

        // 🔥 Popularity
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.fire), // ✅ Pastikan ada gambar atau ganti dengan ikon bawaan
                contentDescription = "Popularity",
                tint = Color(0xFFFFA500),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = popularity,
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}


//@Preview
//@Composable
//private fun ShowItemCardPreview() {
//    JapriTvTheme {
//        val shows =
//            Show(
//                title = "Money Heist: Korea. Joint Economic Area",
//                imageResId = R.drawable.title_card,
//                badge = "TOP 10",
//                rating = "4.8",
//                genres = listOf("Action", "Crime"),
//                duration = "2h 10m",
//                popularity = "17.8K",
//                rank = 1
//            )
//
//        CardRating(
//            show = shows,
//            rank = shows.rank?: 0,  // Menghindari null
//            popularity = shows.popularity ?: "0" // Menghindari null
//        )
//    }
//}