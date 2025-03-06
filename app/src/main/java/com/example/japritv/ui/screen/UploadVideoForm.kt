package com.example.japritv.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.japritv.ui.components.DynamicActionButton
import com.example.japritv.ui.components.uploadvideo.AddEpisodeButton
import com.example.japritv.ui.components.uploadvideo.EpisodeUploadComponent
import com.example.japritv.ui.components.uploadvideo.WarningUpload
import com.example.japritv.ui.theme.JapriTvTheme

@Composable
fun UploadVideoForm(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFFFFF)) // Background color (light yellow)
            .padding(16.dp)
    ) {

        Column {
            WarningUpload()
            EpisodeUploadComponent(
                MovieTitle = "Squid Game",
                episodeTitle = "Episode 1",
                fileName = "Klik untuk mengupload",
                fileSize = "Maks. ukuran file: 75MB | Jenis file: MP4, MPG",
                isUploading = false,
                onFileUploadClick = { },
                progress = 1f
            )
            AddEpisodeButton(onClick = {})

            Spacer(modifier = Modifier.height(12.dp))

//            DynamicActionButton(
//                text = "Lanjut"
//            ) { }
        }
    }
}

@Preview
@Composable
private fun UploadVideoFormPreview() {
    JapriTvTheme {
        UploadVideoForm()
    }
}