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
fun ScaffoldWithButton(navController: NavController,navigationRoute:String ,content: @Composable () -> Unit,titleButton:String,contentTop: @Composable () -> Unit,colorButton:Color,colorTextButton:Color,modifier: Modifier,containerColor:Color) {
    Scaffold(
        containerColor = containerColor,
        topBar = {
            contentTop()
        },
        bottomBar = { Box(modifier = Modifier.padding(16.dp).fillMaxWidth()){
            CustomBoxButton(onClick = {  navController.navigate(navigationRoute)}, title = titleButton, colorBackground = colorButton, colorText = colorTextButton, modifier = modifier)
        }


        }
    ) {
        Box(modifier = Modifier.fillMaxWidth().padding(vertical = 30.dp)){
            content()
        }
    }
}

