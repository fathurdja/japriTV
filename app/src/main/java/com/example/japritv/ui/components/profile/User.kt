package com.example.japritv.ui.components.profile

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.japritv.R

@Composable
fun UserProfile(nameUser: String, email: String,picture:String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Gray, CircleShape)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(picture ?: R.drawable.frame_3452979) // Gunakan default jika null
                    .crossfade(true)
                    .build(),
                contentDescription = "User Avatar",
                modifier = Modifier.fillMaxSize()
            )


        }

        Spacer(modifier = Modifier.width(8.dp))

        // User Information
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Wandy Roseandy",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "User",
                    color = Color.Black,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(24))
                        .background(Color.Gray)

                        .padding(horizontal = 18.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "wendyroseandy@gmail.com",
                color = Color.White,
                fontSize = 12.sp
            )
            Text(
                text = "08123456789",
                color = Color.White,
                fontSize = 12.sp
            )
        }

        // Edit Icon
        Icon(
            painter = painterResource(id = R.drawable.vector__15_),
            contentDescription = "Edit Icon",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

// Preview function
@Preview(showBackground = true)
@Composable
fun UserProfilePreview() {
    UserProfile(
        nameUser = "Wandy Roseandy",
        email = "wendyroseandy@gmail.com",
        picture = "https://example.com/profile.jpg"
    )
}