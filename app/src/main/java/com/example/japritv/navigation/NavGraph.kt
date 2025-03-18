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
import com.example.japritv.ui.components.ScaffoldWithButton
import com.example.japritv.ui.components.ScaffoldWithoutButton

import com.example.japritv.ui.screen.HomeScreen
import com.example.japritv.ui.screen.InstruksiBayarScreen
import com.example.japritv.ui.screen.LoginScreen
import com.example.japritv.ui.screen.MetodeBayarScreen
import com.example.japritv.ui.screen.PaymentScreen
import com.example.japritv.ui.screen.ProfileScreen
import com.example.japritv.ui.screen.RatingScreen
import com.example.japritv.ui.screen.RiwayatScreen
import com.example.japritv.ui.screen.SplashScreen
import com.example.japritv.ui.screen.TokoJapriTV
import com.example.japritv.ui.screen.UpComingScreen
import com.example.japritv.ui.screen.UploadVideoForm
import com.example.japritv.ui.screen.UploadVideoScreen
import com.example.japritv.ui.screen.VideoScreen
import com.example.japritv.ui.screen.VideoVerticalPagerScreen
import com.example.japritv.viewmodel.PaymentViewModel
import com.example.japritv.viewmodel.ShowItemViewModel
import com.example.japritv.viewmodel.VideoViewModel

@Composable
fun NavGraph(navController: NavController, paddingValues: PaddingValues,video: VideoViewModel,data:ShowItemViewModel) {


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
            composable("nowPlaying"){
                VideoVerticalPagerScreen()
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
                ScaffoldWithButton(
                    navController = navController,
                    containerColor = Color.White,
                    navigationRoute = "Pembayaran",
                    titleButton = "Lanjutkan",
                    content = { UploadVideoForm() },
                    contentTop = {
                        HeaderRightWithIcon(
                            "Upload Video",
                            Color.White,
                            Color.Black,
                            R.drawable.vector__8_,
                            onBackClick = { navController.popBackStack() })
                    },
                    colorButton = Color(0xFFD32F2F),
                    colorTextButton = Color.White,
                    modifier = Modifier
                )
            }
            composable("Pembayaran") {
                ScaffoldWithButton(
                    navController = navController,
                    containerColor = Color.White,
                    navigationRoute = "MetodeBayar",
                    titleButton = "Lanjut Pilih Metode Pembayaran",
                    contentTop = {
                        HeaderRightWithIcon(
                            "Konfirmasi Pembayaran",
                            Color.White,
                            Color.Black,
                            R.drawable.vector__9_,
                            onBackClick = { navController.popBackStack() })
                    },
                    content = { PaymentScreen() },
                    colorButton = Color(0xFFD32F2F),
                    colorTextButton = Color.White,
                    modifier = Modifier
                )


            }
            composable("MetodeBayar") {
                val viewModel = viewModel<PaymentViewModel>()
                ScaffoldWithoutButton(
                    containerColor = Color.White,
                    contentTop = {
                        HeaderRightWithIcon(
                            "Konfirmasi Pembayaran",
                            Color.White,
                            Color.Black,
                            R.drawable.vector__9_,
                            onBackClick = { navController.popBackStack() })
                    },
                    content = {
                        MetodeBayarScreen(
                            viewModel,
                            navigateTo = { navController.navigate("InstruksiBayar") })
                    },

                    )


            }
            composable("InstruksiBayar") {
                val viewModel = viewModel<PaymentViewModel>()
                ScaffoldWithoutButton(
                    containerColor = Color.White,
                    contentTop = {
                        HeaderRightWithIcon(
                            "Instruksi Pembayaran",
                            Color.White,
                            Color.Black,
                            R.drawable.vector__9_,
                            onBackClick = { navController.popBackStack() })
                    },
                    content = {
                        InstruksiBayarScreen(
                            colortext = Color(0XFFD22F26),
                            colorButton = Color.White,
                            onClick = { navController.navigate("home") },
                            onClickBack = { navController.popBackStack() })
                    },

                    )


            }


        }
        composable("history"){RiwayatScreen(videoViewModel = video, onClick = {navController.navigate("MetodeBayarBlack") })}
        navigation(startDestination = "MetodeBayar", route = "riwayatRoute"){
            composable("MetodeBayarBlack") {
                val viewModel = viewModel<PaymentViewModel>()
                ScaffoldWithoutButton(
                    containerColor = Color.Black,
                    contentTop = {
                        HeaderRightWithIcon(
                            "Konfirmasi Pembayaran",
                            Color.Black,
                            Color.White,
                            R.drawable.arrowwhite,
                            onBackClick = { navController.popBackStack() })
                    },
                    content = {
                        MetodeBayarScreen(
                            viewModel,
                            navigateTo = { navController.navigate("InstruksiBayarBlack") })
                    },

                    )


            }
            composable("InstruksiBayarBlack") {
                val viewModel = viewModel<PaymentViewModel>()
                ScaffoldWithoutButton(
                    containerColor = Color.Black,
                    contentTop = {
                        HeaderRightWithIcon(
                            "Instruksi Pembayaran",
                            Color.Black,
                            Color.White,
                            R.drawable.arrowwhite,
                            onBackClick = { navController.popBackStack() })
                    },
                    content = {
                        InstruksiBayarScreen(
                            colortext = Color.White,
                            colorButton = Color.Black,
                            onClick = { navController.navigate("home") },
                            onClickBack = { navController.popBackStack() })
                    },

                    )


            }

        }

        composable("profile") { ProfileScreen(navController) }
        navigation(startDestination = "riwayatPembelian", route = "profileScreen") {
            composable("login") {
                LoginScreen(onClick = { navController.navigate("home") })
            }
            composable("TokoJapri"){
                ScaffoldWithButton(
                    navController = navController ,
                    containerColor = Color.Black,
                    navigationRoute = "MetodeBayar",
                    content = {TokoJapriTV()},
                    titleButton = "Lanjut Ke Pembayaran",
                    contentTop = {HeaderRightWithIcon(
                        title = "Toko Japri Tv",
                        color = Color.Black,
                        textColor = Color.White,
                        resId = R.drawable.arrowwhite,
                        onBackClick = {navController.popBackStack()}
                    )},
                    colorButton = Color(0XFFD22F26),
                    colorTextButton = Color.White,
                    modifier = Modifier

                )
            }
        }
    }
}
