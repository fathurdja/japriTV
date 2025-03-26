package com.example.japritv.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.japritv.R
import com.example.japritv.Repository.AuthRepository
import com.example.japritv.Repository.ProfileRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.model.Show
import com.example.japritv.navigation.NavGraph
import com.example.japritv.provider.GoogleSignInHelper
import com.example.japritv.ui.components.*
import com.example.japritv.ui.components.home.CardRating
import com.example.japritv.ui.components.home.Category
import com.example.japritv.ui.components.home.CustomSearchBox
import com.example.japritv.ui.components.home.CustomTopBar
import com.example.japritv.ui.components.home.SearchResultList
import com.example.japritv.viewmodel.CategoryViewModel
import com.example.japritv.viewmodel.PaymentViewModel
import com.example.japritv.viewmodel.ShowItemViewModel
import com.example.japritv.viewmodel.UploadEpisodeViewModel
import com.example.japritv.viewmodel.UserViewModel
import com.example.japritv.viewmodel.VideoViewModel
import kotlinx.coroutines.launch


@Composable
fun MainScreen(
    videoViewModel: VideoViewModel,
    data: ShowItemViewModel,
    uploadEpisodeViewModel: UploadEpisodeViewModel,
    userViewModel: UserViewModel
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    var searchText by remember { mutableStateOf("") }
    val db = AppDatabase.getDatabase(context)
    var selectedItem by remember { mutableStateOf(0) }
    val categoryViewModel: CategoryViewModel = viewModel()
    val showItemViewModel: ShowItemViewModel = viewModel()
    val paymentViewModel: PaymentViewModel = viewModel()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val userInfo by userViewModel.userInfo.collectAsState()
    val subscriptionInfo by userViewModel.subscriptionInfo.collectAsState()
    val coroutineScope = rememberCoroutineScope()



    LaunchedEffect(userInfo) {
        userViewModel.loadSubscriptionInfo(
            db = db,
        )
        val video = videoViewModel.fetchVideos()
        println(video)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Color.Black,
            topBar = {
                if (currentRoute in listOf("home", "terlaris", "rating", "segera_tayang")) {
                    CustomTopBar(
                        searchText = searchText,
                        onSearchTextChanged = { newText -> searchText = newText },
                        navController = navController,
                        categoryViewModel = categoryViewModel,
                        showItemViewModel = showItemViewModel
                    )
                }
            },
            bottomBar = {
                if (currentRoute in listOf(
                        "home", "terlaris", "rating", "segera_tayang",
                        "video", "upload", "history", "profile", "nowPlayingScreen"
                    )
                ) {
                    BottomNavigationBar(
                        selectedItem = selectedItem,
                        onItemSelected = { index ->
                            selectedItem = index
                            when (index) {
                                0 -> navController.navigate("home")
                                1 -> navController.navigate("video")
                                2 -> navController.navigate("upload")
                                3 -> navController.navigate("history")
                                4 -> navController.navigate("profile")
                            }
                        },
                        navController = navController
                    )
                }
            }
        ) { paddingValues ->
            NavGraph(
                navController = navController,
                paddingValues = paddingValues,
                video = videoViewModel,
                data = data,
                db = db,
                uploadEpisodeViewModel = uploadEpisodeViewModel,
                userViewModel = userViewModel,
                paymentViewModel = paymentViewModel
            )
        }

        // 🔥 Jika belum membayar, tampilkan InstruksiBayarScreen
        if (subscriptionInfo != null && !subscriptionInfo!!.isPayed) {

            ScaffoldWithoutButton(
                containerColor = Color.White,
                contentTop = {},
                content = {
                    InstruksiBayarScreen(
                        onClick = {
                            coroutineScope.launch {
                                val newsubscriptionInfo = userViewModel.subscriptionInfo.value
                                val googleSignInHelperag = GoogleSignInHelper(context)
                                val googleAccount = googleSignInHelperag.getGoogleAccount()
                                Log.e("MainScreen", "newsubscriptionInfo: $newsubscriptionInfo")
                                Log.e("MainScreen", "token: ${googleAccount?.token}")

                                if (newsubscriptionInfo != null && googleAccount != null) {

                                    val payment = ProfileRepository.updateDataSubscription(
                                       id =newsubscriptionInfo._id,
                                        db = db
                                    )
                                    userViewModel.subscriptionInfo.value = payment

                                    Toast.makeText(context, "Berhasil membayar", Toast.LENGTH_SHORT)
                                        .show()

                                    // 🔥 Hapus layar sebelumnya agar tidak kembali ke InstruksiBayarScreen
                                    navController.navigate("home") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                    }
                                } else {
                                    Toast.makeText(context, "Gagal membayar", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        },

                        onClickBack = { navController.popBackStack() },
                        colortext = Color.Black,
                        colorButton = Color.Gray,
                        dataPayment = subscriptionInfo!!,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    )
                }
            )

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
