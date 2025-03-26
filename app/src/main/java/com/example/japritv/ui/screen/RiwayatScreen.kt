package com.example.japritv.ui.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.japritv.ui.components.CustomBoxButton
import com.example.japritv.ui.components.Header
import com.example.japritv.ui.components.home.ShowsGridSection
import com.example.japritv.ui.components.video.ModalityContainer
import com.example.japritv.viewmodel.VideoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RiwayatScreen(videoViewModel: VideoViewModel,onClick: (String, String) -> Unit) {
    val riwayatMovies by videoViewModel.dataList.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    val scope = rememberCoroutineScope()
    var selectedCoin by remember { mutableStateOf("") }
    var selectedPrice by remember { mutableStateOf("") }

    var showSheet by remember { mutableStateOf(false) }

    // ✅ State untuk modal


    Scaffold(
        containerColor = Color.Black,
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Header("Riwayat Tontonan", Color.Black, Color.White)
            }
        },

        ) {
        Box(
            modifier = Modifier.padding(
                top = 70.dp
            )
        ) {
            ShowsGridSection(
                riwayatMovies,
                onClick = {  showSheet = true  }
            )
        }

        if (showSheet) {
            ModalBottomSheet(
                containerColor = Color.Black,
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showSheet = false },

            ) {
                ModalityContainer(onClick = { onClick(selectedCoin, selectedPrice) },
                    selectedCoin = selectedCoin,
                    onCoinSelected = { coin, price ->
                        selectedCoin = coin
                        selectedPrice = price
                    })

            }
        }
    }
}

