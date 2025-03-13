package com.example.japritv.ui.screen



import android.annotation.SuppressLint
import android.window.SplashScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.japritv.R
import com.example.japritv.ui.theme.JapriTvTheme


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(false) }

    // Animasi fade-in logo
    LaunchedEffect(Unit) {
        isVisible = true
        delay(2000) // Delay selama 2 detik
        isVisible = false
        delay(500)
        navController.navigate("home") {
            popUpTo("Splash") { inclusive = true } // Hapus Splash dari stack
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(),

            ) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.japripay), // Sesuaikan dengan logo JapriTV
                        contentDescription = "Japri TV Logo",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Japri TV",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }




        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    JapriTvTheme {
        SplashScreen(navController = NavController(LocalContext.current))

    }
}