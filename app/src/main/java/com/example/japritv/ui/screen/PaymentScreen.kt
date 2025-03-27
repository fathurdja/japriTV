package com.example.japritv.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.japritv.R
import com.example.japritv.dao.AppDatabase
import com.example.japritv.ui.components.Header
import com.example.japritv.ui.components.HeaderRightWithIcon
import com.example.japritv.ui.components.ScaffoldWithButton

import com.example.japritv.ui.components.payment.DetailPembayaranCard
import com.example.japritv.ui.components.uploadvideo.WarningUpload
import com.example.japritv.viewmodel.UploadEpisodeViewModel
import com.example.japritv.viewmodel.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PaymentScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    db: AppDatabase
) {
    val video by userViewModel.unreleasedVideos.collectAsState()

    LaunchedEffect(Unit) {
        userViewModel.getVideoUploaded(db = db)
    }

    // Hitung total jumlah episode dari semua video yang belum dirilis
    val totalEpisode = video.sumOf { it.totalEpisode }

    // Hitung total harga dari semua video yang belum dirilis
    val totalHarga = video.sumOf { it.price }

    Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 50.dp)) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding()
        ) {
            WarningUpload(text = "Pastikan sudah sesuai sebelum melanjutkan")
            DetailPembayaranCard(
                jumlahEps = totalEpisode,   // Total episode dari semua video
                biayaPerEpisode = totalHarga // Total harga dari semua video
            )
        }
    }

    if (video.isEmpty()){
        CircularProgressIndicator()
    }
}


//
//@Preview
//@Composable
//private fun PaymentScreenPreview() {
//    val navController = rememberNavController()
//
//    ScaffoldWithButton(
//        navController = navController,
//        navigationRoute = "",
//        titleButton = "Lanjut Pilih Metode Pembayaran",
//        contentTop = {
//            HeaderRightWithIcon(
//                "Konfirmasi Pembayaran",
//                Color.White,
//                Color.Black,
//                R.drawable.vector__9_,
//                onBackClick = { navController.popBackStack() })
//        },
//        content = { PaymentScreen() }
//                ,colorButton = Color(0xFFD32F2F),
//        colorTextButton = Color.White,
//        modifier = Modifier
//    )
//}