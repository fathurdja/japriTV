package com.example.japritv.ui.components.uploadvideo

import LoadingUpload
import android.graphics.DashPathEffect

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.japritv.R
import com.example.japritv.ui.theme.JapriTvTheme
import androidx.compose.ui.graphics.PathEffect
import kotlinx.coroutines.delay


@Composable
fun EpisodeUploadComponent(
    MovieTitle: String,
    episodeTitle: String,
    fileName: String,
    fileSize: String,
    isUploading: Boolean,
    onFileUploadClick: () -> Unit,
    progress: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
    ) {
        // Header box with gray background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF2F2F2))
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Change icon color to green when upload is complete
                val iconTint = if (isUploading && progress < 1f) Color.Gray else Color.Green
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Completed Icon",
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = episodeTitle,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Content box with form fields
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(16.dp)
                .zIndex(1f)
        ) {
            Column {
                // Title input field
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = MovieTitle,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isUploading && progress < 1f){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .drawBehind {
                                drawRect(
                                    color = Color(0xFFE0E0E0),
                                    style = Stroke(
                                        width = 10f,
                                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                                    )
                                )
                            }
                            .clickable {
                                onFileUploadClick()
                            }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.avatar),  // Replace with your add icon
                                contentDescription = "Add File",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = fileName,
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = fileSize,
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    // Show the upload progress when uploading
                    if (progress > 0f) {
                        LoadingUpload(progress = progress)
                    }
                }
                // File upload box with dashed border
                // Once upload is complete (isUploading = false and progress = 100), replace the box
                else {
                    VideoItemUploaded(
                        fileName = "assets.zip",
                        fileSize = "5.3MB",
                        fileIcon = R.drawable.video_vector_icon_1, // Example icon resource
                        onRemoveClick = { /* Handle file removal logic */ }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun EpisodeUploadComponentPreview() {
    JapriTvTheme {
        EpisodeUploadComponent(
            MovieTitle = "Squid Game",
            episodeTitle = "Episode 1",
            fileName = "Klik untuk mengupload",
            fileSize = "Maks. ukuran file: 75MB | Jenis file: MP4, MPG",
            isUploading = false,
            onFileUploadClick = { },
            progress = 1f
        )
    }
}








