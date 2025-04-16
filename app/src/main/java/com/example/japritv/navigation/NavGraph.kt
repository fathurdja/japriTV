package com.example.japritv.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.japritv.R
import com.example.japritv.Repository.AuthRepository
import com.example.japritv.Repository.ProfileRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.model.GoogleAccount
import com.example.japritv.model.PaymentData
import com.example.japritv.model.subscriptionData
import com.example.japritv.provider.GoogleAuthUiProvider
import com.example.japritv.provider.GoogleSignInHelper
import com.example.japritv.ui.components.Header
import com.example.japritv.ui.components.HeaderRightWithIcon
import com.example.japritv.ui.components.ScaffoldWithButton
import com.example.japritv.ui.components.ScaffoldWithoutButton
import com.example.japritv.ui.components.profile.LanguageSelectionScreen
import com.example.japritv.ui.components.profile.TermsAndConditionsScreen
import com.example.japritv.ui.screen.HistoryPembayaran

import com.example.japritv.ui.screen.HomeScreen
import com.example.japritv.ui.screen.InstruksiBayarScreen
import com.example.japritv.ui.screen.LoginScreen
import com.example.japritv.ui.screen.MetodeBayarScreen
import com.example.japritv.ui.screen.PaymentScreen
import com.example.japritv.ui.screen.ProfileScreen
import com.example.japritv.ui.screen.RatingScreen
import com.example.japritv.ui.screen.RegistrationForm
import com.example.japritv.ui.screen.RiwayatPembelian
import com.example.japritv.ui.screen.RiwayatScreen
import com.example.japritv.ui.screen.SplashScreen
import com.example.japritv.ui.screen.TokoJapriTV
import com.example.japritv.ui.screen.UpComingScreen
import com.example.japritv.ui.screen.UploadVideoForm
import com.example.japritv.ui.screen.UploadVideoScreen
import com.example.japritv.ui.screen.VideoScreen
import com.example.japritv.ui.screen.VideoVerticalPagerScreen
import com.example.japritv.ui.screen.WebViewScreen

import com.example.japritv.viewmodel.PaymentViewModel
import com.example.japritv.viewmodel.ShowItemViewModel
import com.example.japritv.viewmodel.UploadEpisodeViewModel
import com.example.japritv.viewmodel.UserViewModel
import com.example.japritv.viewmodel.VideoViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.net.URLDecoder

@Composable
fun NavGraph(
    navController: NavController,
    paddingValues: PaddingValues,
    video: VideoViewModel,
    data: ShowItemViewModel,
    db: AppDatabase,
    uploadEpisodeViewModel: UploadEpisodeViewModel,
    userViewModel: UserViewModel,
    paymentViewModel: PaymentViewModel,
    setSelectedItem: (Int) -> Unit
) {
    val context = LocalContext.current
    val datasubs: subscriptionData
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val credentialManager: CredentialManager = remember { CredentialManager.create(context) }
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

        composable("nowPlayingHistory/{Id}/{index}") { backStackEntry ->
            val Id = backStackEntry.arguments?.getString("Id") ?: return@composable
            val index = backStackEntry.arguments?.getInt("index") ?: return@composable
            VideoVerticalPagerScreen(
                viewModel = video,
                Id = Id,
                db = db,
                index = index,
                onClickBack = { navController.navigate("home")
                              setSelectedItem(0)
                              },
                userViewModel = userViewModel,
                login = { navController.navigate("login") },
                topUpsaldo = { navController.navigate("TokoJapri") })
        }

        composable("home") {
            HomeScreen(navController = navController, videoViewModel = video)
            setSelectedItem(0)
        }
        composable("login") {
            LoginScreen(
                userViewModel = userViewModel,
                navController = navController,
                onSuccess = {
                    setSelectedItem(0)
                }
            )
        }
        composable("video") {
            VideoScreen(viewModel = video, onClick = { Id ->
                navController.navigate(
                    "nowPlaying/$Id"

                )
                println(Id)
            }, db = db)
        }
        // Sub-navigation for "home"
        navigation(startDestination = "terlaris", route = "home") {
            composable("terlaris") {
                HomeScreen(navController = navController, videoViewModel = video)
            }
            composable("nowPlaying/{Id}") { backStackEntry ->
                val Id = backStackEntry.arguments?.getString("Id") ?: return@composable
                VideoVerticalPagerScreen(
                    viewModel = video,
                    Id = Id,
                    db = db,
                    onClickBack = { navController.popBackStack() },
                    userViewModel = userViewModel,
                    login = { navController.navigate("login") },
                    topUpsaldo = { navController.navigate("TokoJapri") })
            }
            composable("nowPlayingHistory/{Id}/{index}") { backStackEntry ->
                val Id = backStackEntry.arguments?.getString("Id") ?: return@composable
                val index = backStackEntry.arguments?.getInt("index") ?: return@composable
                VideoVerticalPagerScreen(
                    viewModel = video,
                    Id = Id,
                    db = db,
                    index = index,
                    onClickBack = { navController.popBackStack() },
                    userViewModel = userViewModel,
                    login = { navController.navigate("login") },
                    topUpsaldo = { navController.navigate("TokoJapri") })
            }
            composable("rating") {
                RatingScreen(videoViewModel = video)
            }
            composable("segera_tayang") {
                UpComingScreen(shows = data.shows, navController = navController)
            }
        }
        composable("upload") {
            UploadVideoScreen(
                userViewModel = userViewModel,
                login = {
                    userViewModel.getUserRole(
                        db = db,
                        onSuccess = {
                            navController.navigate("home")
                        },
                        onError = {
                            Toast.makeText(
                                context,
                                "Gagal mendapatkan peran pengguna minimum saldo 250.000",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                },
                navController = navController,
                db = db
            )
        }
        navigation(startDestination = "uploadEpisode", route = "uploadNavigation") {
            composable("uploadEpisode") {
                ScaffoldWithButton(
                    navController = navController,
                    containerColor = Color.White,
                    navigationRoute = "Pembayaran",
                    titleButton = "Lanjutkan",
                    content = { UploadVideoForm(uploadVideoViewModel = uploadEpisodeViewModel) },
                    contentTop = {
                        HeaderRightWithIcon(
                            "Upload Video",
                            Color.White,
                            Color.Black,
                            R.drawable.vector__8_,
                            onBackClick = { navController.popBackStack() }
                        )
                    },
                    colorButton = Color(0xFFD32F2F),
                    colorTextButton = Color.White,
                    modifier = Modifier,
                    onClick = {
                        val allFilled = uploadEpisodeViewModel.episodes.all { it.fileName != null }
                        val episodeList = uploadEpisodeViewModel.episodes

                        if (episodeList.isNotEmpty() && allFilled) {
                            uploadEpisodeViewModel.uploadVideoToServer(
                                title = episodeList.first().movieTitle,
                                videoFiles = uploadEpisodeViewModel.getAllUploadedFiles(),
                                onSuccess = { videoId, totalEpisode ->
                                    Log.d("Upload", "Video Uploaded Successfully id: $videoId")
                                    Log.d("Upload", "Total Episodes Uploaded: $totalEpisode")
                                    userViewModel.getVideoUploaded(db)
                                },
                                onFailure = { error ->
                                    Log.e("Upload", "Upload Failed: $error")
                                }
                            )
                        } else {
                            Log.e("Upload", "Semua episode harus diisi videonya!")
                        }
                    }


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
                    content = {
                        PaymentScreen(
                            modifier = Modifier,
                            uploadEpisodeViewModel = uploadEpisodeViewModel,
                        )
                    },
                    colorButton = Color(0xFFD32F2F),
                    colorTextButton = Color.White,
                    modifier = Modifier,
                    onClick = {
                        coroutineScope.launch {
                            userViewModel.getVideoUploaded(db = db)
                        }
                    }
                )


            }
            composable("MetodeBayar") {
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
                            paymentViewModel,
                            navigateTo = { type, bank ->
                                paymentViewModel.makePaymentVideo(
                                    type = type,
                                    db = db,
                                    bank = bank,
                                    onSuccess = {
                                        Toast.makeText(
                                            context,
                                            "payment data added",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navController.navigate("InstruksiBayar")
                                    },
                                    onError = {
                                        Toast.makeText(
                                            context,
                                            "failed make payment",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )


                            }
                        )
                    },

                    )


            }
            composable("InstruksiBayar") {
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
                            onClickBack = { navController.popBackStack() },
                            paymentViewModel = paymentViewModel,
                            db = db
                        )
                    },

                    )


            }
        }
        composable("history") {
            RiwayatScreen(
                videoViewModel = video,
                onClick = { selectedCoin, selectedPrice ->
                    navController.navigate("MetodeBayarBlack/$selectedCoin/$selectedPrice")
                },
                navController = navController
            )
        }
        navigation(startDestination = "MetodeBayar", route = "riwayatRoute") {
            composable("MetodeBayarBlack/{coin}/{price}") { backStackEntry ->

                val coin = backStackEntry.arguments?.getString("coin") ?: ""
                val price = backStackEntry.arguments?.getString("price") ?: ""

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
                            paymentViewModel,
                            navigateTo = { type, bank ->
                                coroutineScope.launch {
                                    val level = userViewModel.selectedMembership.value
                                    val datasubscription = userViewModel.subscriptionInfo.value
                                    val koin = userViewModel.selectedkoin.value
                                    if (level != null && koin == 0) {
                                        paymentViewModel.topUpSaldoSubscription(
                                            type = type,
                                            db = db,
                                            level = level,
                                            bank = bank,
                                            onSuccess = {
                                                navController.navigate("InstruksiBayarSubscriptionOrCoins")
                                            },
                                            onError = {
                                                Toast.makeText(
                                                    context,
                                                    "Gagal Membeli Membership",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                        Toast.makeText(
                                            context,
                                            "Berhasil Membeli Membership",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else if (
                                        datasubscription != null
                                    ) {
                                        userViewModel.setSubscriptionInfo(
                                            subscription = datasubscription,
                                            db = db
                                        )
                                        userViewModel.newsubscriptionInfo.value = datasubscription
                                        println(datasubscription.isPayed)
                                        Toast.makeText(
                                            context,
                                            "Berhasil Mengupdate Membership",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else if (koin != 0 && level == null) {
                                        paymentViewModel.topUpSaldoKoin(
                                            type = type,
                                            amount = koin,
                                            db = db,
                                            bank = bank,
                                            onSuccess = {
                                                navController.navigate("InstruksiBayarBlack")
                                            },
                                            onError = {
                                                Toast.makeText(
                                                    context,
                                                    "Gagal top up saldo",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Gagal bertransaksi",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }



                                    {
                                        Toast.makeText(
                                            context,
                                            "Gagal Membeli Membership",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }


                                }
                                navController.navigate("InstruksiBayarBlack")
                            })
                    },

                    )


            }
            composable("InstruksiBayarBlack") {

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
                            onClickBack = { navController.popBackStack() },
                            paymentViewModel = paymentViewModel,
                            db = db

                        )
                    },

                    )


            }

        }
        composable("profile") {
            ProfileScreen(navController, db)
        }
        navigation(startDestination = "riwayatPembelian", route = "profileScreen") {

            composable("TokoJapri") {
                ScaffoldWithButton(
                    navController = navController,
                    containerColor = Color.Black,
                    navigationRoute = "MetodeBayarSubscriptionOrCoins",
                    content = { TokoJapriTV(userViewModel = userViewModel, db = db) },
                    titleButton = "Lanjut Ke Pembayaran",
                    contentTop = {
                        HeaderRightWithIcon(
                            title = "Toko Japri Tv",
                            color = Color.Black,
                            textColor = Color.White,
                            resId = R.drawable.arrowwhite,
                            onBackClick = { navController.popBackStack() }
                        )
                    },
                    colorButton = Color(0XFFD22F26),
                    colorTextButton = Color.White,
                    modifier = Modifier,
                    onClick = {
                        val koin = userViewModel.selectedkoin.value
                        val selectedMember = userViewModel.selectedMembership.value
                        val nominal = userViewModel.nominal.value
//                        Toast.makeText(
//                            context,
//                            "$koin $selectedMember $nominal ",
//                            Toast.LENGTH_SHORT
//                        ).show()
                    }


                )
            }
            composable("MetodeBayarSubscriptionOrCoins") {

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
                            paymentViewModel,
                            navigateTo = { type, bank ->
                                coroutineScope.launch {

                                    val level = userViewModel.selectedMembership.value
                                    val datasubscription = userViewModel.subscriptionInfo.value
                                    val koin = userViewModel.selectedkoin.value

                                    val latestpayment = paymentViewModel.lasttransactionInfo
                                    if (level != null && koin == 0 && latestpayment.value == null) {
                                        paymentViewModel.topUpSaldoSubscription(
                                            type = type,
                                            db = db,
                                            level = level,
                                            bank = bank,
                                            onSuccess = {
                                                Toast.makeText(
                                                    context,
                                                    "Membership Berhasil di order",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                navController.navigate("InstruksiBayarSubscriptionOrCoins")
                                            },
                                            onError = {
                                                Toast.makeText(
                                                    context,
                                                    "Membership Aktif",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                    } else if (
                                        datasubscription != null
                                    ) {
                                        userViewModel.setSubscriptionInfo(
                                            subscription = datasubscription,
                                            db = db
                                        )
                                        userViewModel.newsubscriptionInfo.value = datasubscription
                                        println(datasubscription.isPayed)
                                        Toast.makeText(
                                            context,
                                            "Berhasil Mengupdate Membership",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else if (koin != 0 && level == null && latestpayment.value == null) {
                                        paymentViewModel.topUpSaldoKoin(
                                            type = type,
                                            amount = koin,
                                            db = db,
                                            bank = bank,
                                            onSuccess = {
                                                navController.navigate("InstruksiBayarSubscriptionOrCoins")
                                            },
                                            onError = {
                                                Toast.makeText(
                                                    context,
                                                    "Gagal top up saldo",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                    }
                                    else if(latestpayment.value != null){
                                        Toast.makeText(
                                            context,
                                            "selesaikan dahulu pembayaran sebelumnya",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    else {
                                        Toast.makeText(
                                            context,
                                            "Gagal bertransaksi",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    {
                                        Toast.makeText(
                                            context,
                                            "Gagal Membeli Membership",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }


                                }
                                navController.navigate("InstruksiBayarSubscriptionOrCoins")
                            }

                        )

                    },
                    )


            }
            composable("InstruksiBayarSubscriptionOrCoins") {
                ScaffoldWithoutButton(
                    containerColor = Color.White,
                    contentTop = {
                        HeaderRightWithIcon(
                            "Instruksi Pembayaran",
                            Color.White,
                            Color.Black,
                            R.drawable.vector__9_,
                            onBackClick = {  navController.popBackStack()
                                navController.popBackStack() })
                    },
                    content = {
                        InstruksiBayarScreen(
                            colortext = Color(0XFFD22F26),
                            colorButton = Color.White,
                            onClick = {
                                navController.navigate("home")
                                setSelectedItem(0)
                            },
                            onClickBack = {
                                navController.popBackStack()
                                navController.popBackStack()
                                },
                            paymentViewModel = paymentViewModel,
                            db = db
                        )
                    },
                )


            }
            composable("riwayatPembelian") {
                ScaffoldWithoutButton(
                    content = {
                        RiwayatPembelian(
                            navController = navController,
                            paymentViewModel = paymentViewModel
                        )
                    },
                    contentTop = {
                        HeaderRightWithIcon(
                            title = "Riwayat Pembelian",
                            color = Color.Black,
                            textColor = Color.White,
                            resId = R.drawable.arrowwhite,
                            onBackClick = { navController.popBackStack() }
                        )
                    },
                    containerColor = Color.Black
                )

            }
            composable("kebijakanPrivasi") {
                ScaffoldWithoutButton(
                    containerColor = Color.Black,
                    content = { TermsAndConditionsScreen(updatedDate = "16 maret 2025") },
                    contentTop = {
                        HeaderRightWithIcon(
                            title = "Kebijakan Privasi",
                            color = Color.Black,
                            textColor = Color.White,
                            resId = R.drawable.arrowwhite,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                )
            }
            composable("pengaturanbahasa") {
                ScaffoldWithoutButton(
                    content = { LanguageSelectionScreen() },
                    contentTop = {
                        HeaderRightWithIcon(
                            title = "Pengaturan Bahasa",
                            color = Color.Black,
                            textColor = Color.White,
                            resId = R.drawable.arrowwhite,
                            onBackClick = { navController.popBackStack() }
                        )
                    },
                    containerColor = Color.Black
                )
            }
        }
        composable(
            "instruksi_bayar_screen/{paymentData}",
            arguments = listOf(navArgument("paymentData") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("paymentData")
            val paymentData = Gson().fromJson(json, PaymentData::class.java)
            ScaffoldWithoutButton(
                containerColor = Color.White,
                contentTop = {
                    HeaderRightWithIcon(
                        "Instruksi Pembayaran",
                        Color.White,
                        Color.Black,
                        R.drawable.arrowwhite,
                        onBackClick = { navController.popBackStack() })
                },
                content = {
                    HistoryPembayaran(
                        paymentData = paymentData,
                        colortext = Color.White,
                        colorButton = Color.Black,
                        onClick = { navController.navigate("home")
                            setSelectedItem(0)
                                  },
                        onClickBack = { navController.popBackStack() },
                        paymentViewModel = paymentViewModel,
                        db = db
                    )
                },

                )


        }

        composable("webview/{title}/{url}") { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            val title = backStackEntry.arguments?.getString("title") ?: ""
            WebViewScreen(url = URLDecoder.decode(url, "UTF-8"), title = title)
        }
        composable("registerForm/{referralCode}") { backStackEntry ->

            val referralCode = backStackEntry.arguments?.getString("referralCode") ?: "0"
            RegistrationForm(referralCode = referralCode, onSuccess = {token ->
             coroutineScope.launch {
                val result =  AuthRepository.sendTokenToServer(token,db)
                 if (result){
                     AuthRepository.getDataLogin(db)
                     Toast.makeText(context, "Berhasil Login", Toast.LENGTH_SHORT).show()
                     userViewModel.loadUserInfo()
                     setSelectedItem(0)
                     navController.navigate("home")
                 }else{
                     Toast.makeText(context, "Gagal Login", Toast.LENGTH_SHORT).show()
                 }

             }

            })
        }
    }
}

