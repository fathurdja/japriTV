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
import com.example.japritv.R
import com.example.japritv.ui.components.home.ButtonCustom

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScaffoldWithoutBottomBar(navController: NavController,navigationRoute:String ,content: @Composable () -> Unit,title: String,titleButton:String) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            Header(title, Color.White, Color.Black)
        },
        bottomBar = { Box(){
            CustomBoxButton(onClick = {  navController.navigate(navigationRoute)}, title = titleButton)
        }


         }
    ) {
       Box(modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp)){
           content()
       }
    }
}
