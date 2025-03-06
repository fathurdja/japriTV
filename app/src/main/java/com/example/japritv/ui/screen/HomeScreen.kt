package com.example.japritv.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R
import com.example.japritv.model.Show
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.japritv.ui.components.BottomNavigationBar
import com.example.japritv.ui.components.Category
import com.example.japritv.ui.components.CustomSearchBox
import com.example.japritv.ui.components.MovieItem

import com.example.japritv.ui.theme.JapriTvTheme


@Composable
fun HomeScreen(navController: NavController) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.Black)
        ) {
            // Featured Show ikut scroll ke atas
            item {
                FeaturedShowSection(navController, "Featured Shows", R.drawable.image_7)
            }
            // ShowsGridSection dibatasi tingginya agar tidak infinite
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp) // Sesuaikan tinggi sesuai kebutuhan
                ) {
                    ShowsGridSection()
                }
            }
        }
    }
}




@Composable
fun FeaturedShowSection(navController: NavController, text: String, picture: Int) {
    val shows = listOf(
        Show(
            title = "Breaking Bad",
            imageResId = R.drawable.title_card,
            badge = "TOP 10",
            rating = "4.9",
            genres = listOf("Drama", "Crime"),
            duration = "50m",
            popularity = "20K",
            rank = 2,
            videoResId = R.drawable.image_7,
            description = "Bercerita tentang permainan bertahan hidup yang mematikan."
        ),
        Show(
            title = "Squid Game",
            imageResId = R.drawable.title_card,
            badge = "TOP 10",
            rating = "4.8",
            genres = listOf("Thriller", "Survival"),
            duration = "60m",
            popularity = "50K",
            rank = 1,
            videoResId = R.drawable.image_7,
            description = "Serial ini mengisahkan tentang 456 orang yang berpartisipasi dalam permainan untuk memenangkan hadiah uang tunai."
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        val listState = rememberLazyListState()
        var selectedIndex by remember { mutableStateOf(0) }

        // Perbarui selectedIndex berdasarkan scroll posisi
        LaunchedEffect(listState.firstVisibleItemIndex) {
            selectedIndex = listState.firstVisibleItemIndex
        }

        // Featured Show Carousel
        Box(modifier = Modifier.fillMaxWidth()) {
            LazyRow(
                state = listState, // Gunakan LazyListState
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                contentPadding = PaddingValues(0.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(shows) { index, show ->
                    Box(
                        modifier = Modifier
                            .width(350.dp)
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Image(
                            painter = painterResource(id = show.videoResId),
                            contentDescription = "Featured Show",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }

        // Show Title dan Indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = shows[selectedIndex].title, // Menampilkan judul yang sesuai dengan item aktif
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            // Page Indicator
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(shows.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (index == selectedIndex) 8.dp else 6.dp)
                            .background(
                                color = if (index == selectedIndex) Color.White else Color.Gray,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}


@Composable
fun ShowsGridSection() {
    val shows = listOf(
        Show(
            title = "Breaking Bad",
            imageResId = R.drawable.title_card,  // Ganti dengan resource yang sesuai
            badge = "TOP 10",
            rating = "4.9",
            genres = listOf("Drama", "Crime"),
            duration = "50m",
            popularity = "20K",
            rank = 2,
            videoResId = R.drawable.image_7,
            description = "Bercerita tentang permainan bertahan hidup yang mematikan. Serial ini mengisahkan tentang 456 orang yang berpartisipasi dalam permainan untuk memenangkan hadiah uang tunai."

        ),
        Show(
            title = "Breaking Bad",
            imageResId = R.drawable.title_card,  // Ganti dengan resource yang sesuai
            badge = "TOP 10",
            rating = "4.9",
            genres = listOf("Drama", "Crime"),
            duration = "50m",
            popularity = "20K",
            rank = 2,
            videoResId = R.drawable.image_7,
            description = "Bercerita tentang permainan bertahan hidup yang mematikan. Serial ini mengisahkan tentang 456 orang yang berpartisipasi dalam permainan untuk memenangkan hadiah uang tunai."

        ),
        Show(
            title = "Breaking Bad",
            imageResId = R.drawable.title_card,  // Ganti dengan resource yang sesuai
            badge = "TOP 10",
            rating = "4.9",
            genres = listOf("Drama", "Crime"),
            duration = "50m",
            popularity = "20K",
            rank = 2,
            videoResId = R.drawable.image_7,
            description = "Bercerita tentang permainan bertahan hidup yang mematikan. Serial ini mengisahkan tentang 456 orang yang berpartisipasi dalam permainan untuk memenangkan hadiah uang tunai."

        ),
        Show(
            title = "Breaking Bad",
            imageResId = R.drawable.title_card,  // Ganti dengan resource yang sesuai
            badge = "TOP 10",
            rating = "4.9",
            genres = listOf("Drama", "Crime"),
            duration = "50m",
            popularity = "20K",
            rank = 2,
            videoResId = R.drawable.image_7,
            description = "Bercerita tentang permainan bertahan hidup yang mematikan. Serial ini mengisahkan tentang 456 orang yang berpartisipasi dalam permainan untuk memenangkan hadiah uang tunai."

        ),
        Show(
            title = "Breaking Bad",
            imageResId = R.drawable.title_card,  // Ganti dengan resource yang sesuai
            badge = "TOP 10",
            rating = "4.9",
            genres = listOf("Drama", "Crime"),
            duration = "50m",
            popularity = "20K",
            rank = 2,
            videoResId = R.drawable.image_7,
            description = "Bercerita tentang permainan bertahan hidup yang mematikan. Serial ini mengisahkan tentang 456 orang yang berpartisipasi dalam permainan untuk memenangkan hadiah uang tunai."

        ), Show(
            title = "Breaking Bad",
            imageResId = R.drawable.title_card,  // Ganti dengan resource yang sesuai
            badge = "TOP 10",
            rating = "4.9",
            genres = listOf("Drama", "Crime"),
            duration = "50m",
            popularity = "20K",
            rank = 2,
            videoResId = R.drawable.image_7,
            description = "Bercerita tentang permainan bertahan hidup yang mematikan. Serial ini mengisahkan tentang 456 orang yang berpartisipasi dalam permainan untuk memenangkan hadiah uang tunai."

        ), Show(
            title = "Breaking Bad",
            imageResId = R.drawable.title_card,  // Ganti dengan resource yang sesuai
            badge = "TOP 10",
            rating = "4.9",
            genres = listOf("Drama", "Crime"),
            duration = "50m",
            popularity = "20K",
            rank = 2,
            videoResId = R.drawable.image_7,
            description = "Bercerita tentang permainan bertahan hidup yang mematikan. Serial ini mengisahkan tentang 456 orang yang berpartisipasi dalam permainan untuk memenangkan hadiah uang tunai."

        ),
        Show(
            title = "Breaking Bad",
            imageResId = R.drawable.title_card,  // Ganti dengan resource yang sesuai
            badge = "TOP 10",
            rating = "4.9",
            genres = listOf("Drama", "Crime"),
            duration = "50m",
            popularity = "20K",
            rank = 2,
            videoResId = R.drawable.image_7,
            description = "Bercerita tentang permainan bertahan hidup yang mematikan. Serial ini mengisahkan tentang 456 orang yang berpartisipasi dalam permainan untuk memenangkan hadiah uang tunai."

        )
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),

        contentPadding = PaddingValues(8.dp)
    ) {
        items(shows) { show ->
            MovieItem(show, show.title)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun MainScreenPreview() {
//    JapriTvTheme {
//        MainScreen()
//    }
//}