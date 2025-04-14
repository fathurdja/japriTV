package com.example.japritv.ui.screen

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.japritv.R
import com.example.japritv.model.PaymentData
import com.example.japritv.ui.components.ScaffoldWithoutButton
import com.example.japritv.ui.components.home.Category
import com.example.japritv.ui.components.profile.RiwayatCard
import com.example.japritv.ui.theme.JapriTvTheme
import com.example.japritv.viewmodel.PaymentViewModel
import com.example.japritv.viewmodel.UserViewModel
import com.google.gson.Gson
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun RiwayatPembelian(
    navController: NavController,
    paymentViewModel: PaymentViewModel
) {
    val historyVideo by paymentViewModel.historyTransVideo.collectAsState()
    val historySubscription by paymentViewModel.historyTransSubscription.collectAsState()
    val historyCoin by paymentViewModel.historyTransCoin.collectAsState()

    var selectedMenu by remember { mutableStateOf("Pembelian Video") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isLoading = true
        paymentViewModel.getHistoryTransactionVideo()
        paymentViewModel.getHistoryTransactionSubscription()
        paymentViewModel.getHistoryTransactionCoin()
        delay(300) // Delay agar loading muncul sebentar
        isLoading = false

        println(historyCoin)
        println(historySubscription)
        println(historyVideo)

    }

    fun formatDate(rawDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(rawDate)
            val outputFormat = SimpleDateFormat("dd/MM/yyyy, hh:mm a", Locale.getDefault())
            outputFormat.format(date ?: return "Invalid Date")
        } catch (e: Exception) {
            "Invalid Date"
        }
    }


    val menu = listOf("Pembelian Video", "Pembelian Keanggotaan", "Pembelian Koin")

    Column(modifier = Modifier.padding(16.dp)) {
        LazyRow(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(menu) { item ->
                val isSelected = item == selectedMenu
                Category(
                    text = item,
                    backgroundColor = if (isSelected) Color.Red else Color(0xFF343434),
                    onClick = { selectedMenu = item }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            val dataToShow: List<PaymentData> = when (selectedMenu) {
                "Pembelian Video" -> historyVideo.orEmpty()
                "Pembelian Keanggotaan" -> historySubscription.orEmpty()
                "Pembelian Koin" -> historyCoin.orEmpty()
                else -> emptyList()
            }.sortedByDescending {
                try {
                    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    format.timeZone = TimeZone.getTimeZone("UTC")
                    format.parse(it.createdAt)?.time ?: 0L
                } catch (e: Exception) {
                    0L
                }
            }


            if (dataToShow.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.notfoundpayment),
                            contentDescription = "Not Found",
                            modifier = Modifier.fillMaxWidth(0.6f)
                        )
                        Text(
                            text = "Belum Ada Aktivitas $selectedMenu",
                            color = Color(0xFF6B6B6B)
                        )
                    }
                }
            } else {
                LazyColumn {
                    items(dataToShow) { item ->
                        RiwayatCard(
                            movieName = when(selectedMenu){
                                "Pembelian Video" -> item.name ?: "Unknown Video"
                                "Pembelian Keanggotaan" -> "Anggota ${item.level}" ?: "Unknown Subscription"
                                "Pembelian Koin" -> "Top Up Koin ${item.amount}" ?: "Unknown Coin Purchase"
                                else -> "Unknown"
                            } ,
                            date = formatDate(item.createdAt),
                            harga = "Rp ${item.totalAmount}",
                            image = when (selectedMenu) {
                                "Pembelian Koin" -> R.drawable.crown
                                "Pembelian Keanggotaan" -> R.drawable.crown // ganti dengan icon coin jika ada
                                else -> R.drawable.theaters
                            },
                            status = item.status,
                            onClick = {
                                val json = Uri.encode(Gson().toJson(item))
                                navController.navigate("instruksi_bayar_screen/$json")},
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}




@Preview
@Composable
private fun RiwayatPembelianPreview() {
    JapriTvTheme {
        ScaffoldWithoutButton(
            content = {
//                RiwayatPembelian(navController = NavController(LocalContext.current))
            },
            contentTop ={},
            containerColor = Color.Black
        )

    }
}