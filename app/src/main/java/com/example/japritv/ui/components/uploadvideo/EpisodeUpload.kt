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
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.text.TextStyle
import com.example.japritv.model.Episode
import com.example.japritv.viewmodel.UploadEpisodeViewModel
import kotlinx.coroutines.delay


@Composable
fun EpisodeUploadComponent(
    episodeIndex: Int,
    uploadVideoViewModel: UploadEpisodeViewModel,
    episode: Episode
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 4.dp)
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))


    ) {

        Column {
            // Header box dengan status upload
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
                    val iconTint = when {
                        episode.isUploading && episode.progress < 1f -> Color.Gray  // Masih dalam proses upload
                        episode.progress >= 1f -> Color.Green  // Upload selesai
                        else -> Color.Gray  // Default
                    }
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Completed Icon",
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = episode.episodeTitle,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input untuk judul video
            Column(modifier = Modifier.padding(16.dp)) {
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
                    if (episode.movieTitle.isEmpty()) {
                        Text(
                            text = "Masukkan Judul Video",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    BasicTextField(
                        value = episode.movieTitle,
                        onValueChange = { newValue ->
                            uploadVideoViewModel.updateMovieTitle(episodeIndex, newValue)
                        },
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Kondisi untuk mengganti box upload dengan VideoItemUploaded jika upload selesai
                if (episode.isUploading) {
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
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(
                                                10f,
                                                10f
                                            ), 0f
                                        )
                                    )
                                )
                            }
                            .clickable {
                                uploadVideoViewModel.uploadFile(
                                    episodeIndex,
                                    "example_video.mp4",
                                    "5 MB"
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.avatar),
                                contentDescription = "Upload Icon",

                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Klik untuk mengupload", color = Color.Black, fontSize = 14.sp)
                            Text(text = "Maks. ukuran file: 75 MB | Jenis file: MP4, MPG", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                    // Tampilkan LoadingUpload saat sedang mengunggah
                    Spacer(modifier = Modifier.height(16.dp))
                    LoadingUpload(progress = episode.progress)
                } else if (episode.fileName.isNotEmpty()) {
                    // Jika file sudah selesai diupload, tampilkan VideoItemUploaded
                        VideoItemUploaded(
                            fileName = episode.fileName,
                            fileSize = episode.fileSize,
                            fileIcon = R.drawable.video_vector_icon_1, // Sesuaikan dengan resource yang kamu punya
                            onRemoveClick = {
//                        uploadVideoViewModel.removeFile(episodeIndex)
                            }
                        )

                } else {
                    // Jika tidak ada upload, tampilkan box upload dengan garis putus-putus
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
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(
                                                10f,
                                                10f
                                            ), 0f
                                        )
                                    )
                                )
                            }
                            .clickable {
                                uploadVideoViewModel.uploadFile(
                                    episodeIndex,
                                    "example_video.mp4",
                                    "5 MB"
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.avatar),
                                contentDescription = "Upload Icon",

                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Klik untuk mengupload", color = Color.Black, fontSize = 14.sp)
                            Text(text = "Maks. ukuran file: 75 MB | Jenis file: MP4, MPG", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun EpisodeUploadComponentPreview() {

    val video = UploadEpisodeViewModel()
    JapriTvTheme {
        video.episodes.forEachIndexed { index, episode ->
            EpisodeUploadComponent(
                episodeIndex = index,
                uploadVideoViewModel =video,
                episode = episode
            )
        }
    }
}















