package com.example.japritv.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.japritv.R
import com.example.japritv.model.Show
import com.example.japritv.model.Video
import com.example.japritv.ui.components.*
import com.example.japritv.ui.theme.JapriTvTheme
import com.example.japritv.viewmodel.VideoViewModel



@Composable
fun MainScreen(videoViewModel: VideoViewModel) {
    val navController = rememberNavController()
    var searchText by remember { mutableStateOf("") }

    val categories = remember {
        mutableStateListOf(
            "🔥 Terlaris" to true,
            "Daftar Peringkat" to false,
            "Segera Tayang" to false,
            "Action" to false,
            "Drama" to false,
        )
    }

    val shows = listOf(
        Show(
            title = "Breaking Bad",
            imageResId = R.drawable.title_card,
            badge = "TOP 10",
            rating = "4.9",
            genres = listOf("Drama", "Crime"),
            duration = "50m",
            popularity = "20K",
            rank = 1,
            videoResId = R.drawable.image_7,
            description = "Bercerita tentang permainan bertahan hidup yang mematikan."
        ),
        Show(
            title = "Squid Game",
            imageResId = R.drawable.title_card,
            badge = "TOP 10",
            rating = "4.8",
            genres = listOf("Thriller", "Drama"),
            duration = "55m",
            popularity = "18K",
            rank = 2,
            videoResId = R.drawable.image_7,
            description = "Serial tentang 456 orang yang berpartisipasi dalam permainan mematikan."
        )
    )

    var selectedItem by remember { mutableStateOf(0) }

    val currentRoute by navController.currentBackStackEntryAsState()

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            if (currentRoute?.destination?.route != "video") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .padding(horizontal = 5.dp, vertical = 5.dp)
                ) {
                    CustomSearchBox(
                        searchText = searchText,
                        onTextChanged = { newText -> searchText = newText },
                        onClearClick = { searchText = "" }
                    )
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedItem,
                onItemSelected = { index ->
                    selectedItem = index
                    when (index) {
                        0 -> navController.navigate("home")
                        1 -> navController.navigate("video")
                    }
                },
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.Black)
        ) {
            if (searchText.isNotEmpty()) {
                // **Saat Search Diisi, Tampilkan Rating List**
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    items(shows) { show ->
                        show.rank?.let {
                            show.popularity?.let { popularity ->
                               Box(modifier = Modifier.padding(7.dp)){
                                   CardRating(
                                       rank = it,
                                       show = show,
                                       popularity = popularity
                                   )
                               }
                            }
                        }

                    }
                }
            } else {
                // **Jangan tampilkan kategori jika di halaman video**
                if (currentRoute?.destination?.route != "video") {
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories) { (title, isSelected) ->
                            Category(
                                text = title,
                                backgroundColor = if (isSelected) Color.Red else Color(0xFF343434),
                                onClick = {
                                    categories.replaceAll { (name, _) ->
                                        name to (name == title)
                                    }
                                    when (title) {
                                        "🔥 Terlaris" -> navController.navigate("terlaris")
                                        "Daftar Peringkat" -> navController.navigate("rating")
                                        "Segera Tayang" -> navController.navigate("segera_tayang")
                                    }
                                }
                            )
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = "terlaris"
                ) {
                    composable("terlaris") { HomeScreen(navController) }
                    composable("rating") { RatingScreen(shows = shows, navController = navController) }
                    composable("segera_tayang") { UpComingScreen(shows = shows, navController = navController) }
                    composable("home") { HomeScreen(navController) }
                    composable("video") { VideoScreen(videoViewModel) }
                }
            }
        }
    }
}






//@Preview
//@Composable
//private fun MainScreenPreview() {
//    JapriTvTheme {
//        MainScreen()
//    }
//}
