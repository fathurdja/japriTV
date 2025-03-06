package com.example.japritv.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.japritv.R
import com.example.japritv.model.Show
import com.example.japritv.ui.components.*
import com.example.japritv.ui.components.home.CardRating
import com.example.japritv.ui.components.home.Category
import com.example.japritv.ui.components.home.CustomSearchBox
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
    val title = when (currentRoute?.destination?.route) {
        "upload" -> "Upload Video"
         // For home, this will be empty, so categories and search bar will be shown
        else -> ""
    }
    Scaffold(
        containerColor = Color.Black,
        topBar = {
            // If the current route is 'home', show categories and search bar, else show title
            if (currentRoute?.destination?.route !in listOf("home", "terlaris","rating","segera_tayang")) {
                // If route is home, only show categories and search box
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .padding(horizontal = 5.dp, vertical = 25.dp),
                    verticalArrangement = Arrangement.Center, // Ensures vertical center alignment
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally // Ensures horizontal center alignment
                ) {
                    Text(
                        text = title, // Display title based on route
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,

                    )
                }

            } else {
                // If not 'home', show title based on the current route
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
                    // Categories and search bar
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
                        2 -> navController.navigate("upload")
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
//                if (currentRoute?.destination?.route != "video" && currentRoute?.destination?.route != "upload") {
//                    LazyRow(
//                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
//                        horizontalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        items(categories) { (title, isSelected) ->
//                            Category(
//                                text = title,
//                                backgroundColor = if (isSelected) Color.Red else Color(0xFF343434),
//                                onClick = {
//                                    categories.replaceAll { (name, _) ->
//                                        name to (name == title)
//                                    }
//                                    when (title) {
//                                        "🔥 Terlaris" -> navController.navigate("terlaris")
//                                        "Daftar Peringkat" -> navController.navigate("rating")
//                                        "Segera Tayang" -> navController.navigate("segera_tayang")
//                                    }
//                                }
//                            )
//                        }
//                    }
//                }

                NavHost(
                    navController = navController,
                    startDestination = "terlaris"
                ) {

                    composable("terlaris") { HomeScreen(navController) }
                    composable("rating") { RatingScreen(shows = shows, navController = navController) }
                    composable("segera_tayang") { UpComingScreen(shows = shows, navController = navController) }
                    composable("home") { HomeScreen(navController) }
                    composable("video") { VideoScreen(videoViewModel) }
                    composable("upload") { UploadVideoScreen() }
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
