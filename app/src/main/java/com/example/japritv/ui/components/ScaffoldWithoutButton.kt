package com.example.japritv.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScaffoldWithoutButton(content: @Composable () -> Unit,  contentTop: @Composable () -> Unit,containerColor:Color) {
    Scaffold(
        containerColor = containerColor,
        topBar = {
            contentTop()
        },
    ) {
        Box(modifier = Modifier.fillMaxWidth().padding(vertical = 30.dp)){
            content()
        }
    }
}