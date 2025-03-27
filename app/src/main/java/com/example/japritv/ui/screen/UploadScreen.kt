package com.example.japritv.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.japritv.R
import com.example.japritv.ui.components.DynamicActionButton
import com.example.japritv.ui.components.Header
import com.example.japritv.ui.components.uploadvideo.RequirementsWithLogin
import com.example.japritv.ui.components.uploadvideo.RequirementsWithoutLogin
import com.example.japritv.ui.theme.JapriTvTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UploadVideoScreen(isLoggedIn: Boolean = false
                      ,navController: NavController,login:()->Unit) {
    Scaffold(
        topBar = {
            Box(modifier = Modifier.padding(vertical = 30.dp)) {
                Header("Upload Video",Color.Black,Color.White)
            }
        },
        containerColor = Color.Black, // Set the background color of the entire screen
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black) // Set the background color here
                .padding(top = 30.dp) // Adjust the padding for the content
        ) {
            if (isLoggedIn) {
                Column(
                    modifier = Modifier.align(Alignment.TopCenter), // Align content at the top
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title Text
                    Box(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Saatnya Upload Karyamu!",
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Description Text
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            text = "Kamu udah jadi kreator, sekarang waktunya upload video pertama! Yuk, cek dulu syaratnya biar lancar.",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Image
                    Box(modifier = Modifier.padding(top = 8.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.untitled_design_2048x2048__3__1),
                            contentDescription = "Creator working",
                            modifier = Modifier.size(150.dp)
                        )
                    }

                    // Requirements with login
                    Box(modifier = Modifier.padding(horizontal = 11.dp)) {
                        RequirementsWithLogin()
                    }

                    // Dynamic Action Button
                    Box(modifier = Modifier.padding(horizontal = 15.dp)) {
                        DynamicActionButton(
                            text = "Mulai upload karya",
                            onClick = {navController.navigate("uploadEpisode")}
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title Text
                    Box(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Jadi Kreator & Mulai Upload Video!",
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Description Text
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            text = "Pengen jadi bagian dari kreator eksklusif kami? Yuk, aktifkan akun kreatormu dan mulai upload video!",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Image
                    Box(modifier = Modifier.padding(top = 8.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.untitled_design_2048x2048__1__1),
                            contentDescription = "Creator working",
                            modifier = Modifier.size(220.dp)
                        )
                    }

                    // Requirements without login
                    Box(modifier = Modifier.padding(vertical = 5.dp)) {
                        RequirementsWithoutLogin()
                    }

                    // Dynamic Action Button
                    Box(modifier = Modifier.padding(top = 16.dp)) {
                        DynamicActionButton(
                            text = "Mulai upload karya",
                            onClick = {
                                !isLoggedIn
                                login()
                            }
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun UploadVideoPagePreview() {
    JapriTvTheme {

    }
}
