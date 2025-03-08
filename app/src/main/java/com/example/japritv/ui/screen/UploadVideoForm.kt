package com.example.japritv.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.japritv.ui.components.DynamicActionButton
import com.example.japritv.ui.components.uploadvideo.AddEpisodeButton
import com.example.japritv.ui.components.uploadvideo.EpisodeUploadComponent
import com.example.japritv.ui.components.uploadvideo.WarningUpload
import com.example.japritv.ui.theme.JapriTvTheme
import com.example.japritv.viewmodel.UploadEpisodeViewModel

@Composable
fun UploadVideoForm(modifier: Modifier = Modifier) {
    val uploadVideoViewModel: UploadEpisodeViewModel = viewModel()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFFFFF))
            .padding(vertical = 30.dp)
    ) {
        Column( modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), // Aktifkan scroll
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            WarningUpload()
            uploadVideoViewModel.episodes.forEachIndexed { index, episode ->
                EpisodeUploadComponent(
                    episodeIndex = index,
                    uploadVideoViewModel = uploadVideoViewModel,
                    episode = episode
                )
            }
           Box(modifier = Modifier.padding(horizontal = 10.dp)){
               AddEpisodeButton(onClick = {
                   // Tambahkan episode baru ke daftar
                   uploadVideoViewModel.addEpisode()
               })
           }

            Spacer(modifier = Modifier.height(12.dp))
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