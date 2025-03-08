package com.example.japritv.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.japritv.R
import com.example.japritv.ui.components.Header
import com.example.japritv.ui.components.HeaderRightWithIcon
import com.example.japritv.ui.components.ScaffoldWithoutBottomBar
import com.example.japritv.ui.screen.HomeScreen
import com.example.japritv.ui.screen.RatingScreen
import com.example.japritv.ui.screen.SplashScreen
import com.example.japritv.ui.screen.UpComingScreen
import com.example.japritv.ui.screen.UploadVideoForm
import com.example.japritv.ui.screen.UploadVideoScreen
import com.example.japritv.ui.screen.VideoScreen
import com.example.japritv.viewmodel.ShowItemViewModel
import com.example.japritv.viewmodel.VideoViewModel

@Composable
fun NavGraph(navController: NavController, paddingValues: PaddingValues) {
    val data: ShowItemViewModel = viewModel()
    val video: VideoViewModel = viewModel()

    // Handle routing and navigation
    NavHost(
        navController = navController as NavHostController,
        startDestination = "Splash",
        modifier = Modifier.padding(paddingValues)
    ) {
        // Home Screen and Categories
        composable("Splash") {
            SplashScreen(navController = navController)
        }
        composable("home") {
            HomeScreen(navController = navController)
        }

        // Sub-navigation for "home"
        navigation(startDestination = "terlaris", route = "home") {
            composable("terlaris") {
                HomeScreen(navController = navController)
            }
            composable("rating") {
                RatingScreen(shows = data.shows, navController = navController)
            }
            composable("segera_tayang") {
                UpComingScreen(shows = data.shows, navController = navController)
            }
        }

        // Video and Upload screens
        composable("video") { VideoScreen(video) }


        composable("upload") { UploadVideoScreen(navController = navController) }
        navigation(startDestination = "uploadEpisode", route = "uploadNavigation") {
            composable("uploadEpisode") {
                ScaffoldWithoutBottomBar(
                    navController = navController,
                    title = "Upload Video",
                    navigationRoute = "Pembayaran",
                    titleButton = "Lanjutkan",
                    content = { UploadVideoForm() },
                    contentTop = { HeaderRightWithIcon("Upload Video", Color.White, Color.Black, R.drawable.vector__8_, onBackClick = {navController.popBackStack()}) }

                )
            }
        }
    }
}
