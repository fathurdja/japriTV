package com.example.japritv.ui.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.japritv.R
import com.example.japritv.ui.components.Login.ButtonLogin
import com.example.japritv.ui.components.Login.Footer

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(onClick:()->Unit) {
    Scaffold(
        containerColor = Color.Black,
        bottomBar = {
            Footer()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(vertical = 190.dp),

            ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.japripay), // Sesuaikan dengan logo JapriTV
                    contentDescription = "Japri TV Logo",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Japri TV",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(48.dp))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 19.dp)) {
                    Column {
                        ButtonLogin(
                            onClick = {onClick()},
                            text = "Login dengan Japri Pay",
                            icon = R.drawable.japripay,
                            color = Color(0xFF0033CC)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ButtonLogin(
                            onClick = {onClick()},
                            text = "Login dengan Facebook",
                            icon = R.drawable.path14,
                            color = Color(0xFF3E67B5)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ButtonLogin(
                            onClick = {onClick()},
                            text = "Login dengan Google",
                            icon = R.drawable.logo_googleg_48dp,
                            color = Color(0xFF313131)
                        )
                    }
                }


            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {

    LoginScreen(onClick = {})
}