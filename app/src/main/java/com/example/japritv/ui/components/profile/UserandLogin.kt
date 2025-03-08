package com.example.japritv.ui.components.profile


// Necessary imports
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.japritv.R
import com.example.japritv.ui.theme.JapriTvTheme


@Composable
fun UserInfo(onLoginClick: () -> Unit, onCopyClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Box untuk User Info (Avatar + Nama + ID)
            Box {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.Gray, CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.frame_3452979),
                            contentDescription = "User Avatar",

                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    // Box untuk Nama dan ID
                    Box(modifier = Modifier.padding(vertical = 3.dp, horizontal = 8.dp)) {
                        Column {
                            Text(
                                text = "Pengunjung",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "ID123456789",
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))

                                // Box untuk tombol "Salin"
                                Box(
                                    modifier = Modifier
                                        .background(Color.DarkGray, RoundedCornerShape(10.dp))
                                        .padding(horizontal = 15.dp,)
                                        .clickable { onCopyClick() }
                                ) {
                                    Text(
                                        text = "Salin",
                                        color = Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Box untuk tombol Login
            Box(
                modifier = Modifier
                    .border(1.dp, Color.White, RoundedCornerShape(14.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { onLoginClick() }
            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserInfo() {
    JapriTvTheme {
    UserInfo(onLoginClick = {}, onCopyClick = {})
    }
}



