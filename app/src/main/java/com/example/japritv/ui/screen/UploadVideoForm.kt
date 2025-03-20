package com.example.japritv.ui.screen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.japritv.model.Episode
import com.example.japritv.ui.components.DynamicActionButton
import com.example.japritv.ui.components.uploadvideo.AddEpisodeButton
import com.example.japritv.ui.components.uploadvideo.EpisodeUploadComponent
import com.example.japritv.ui.components.uploadvideo.WarningUpload
import com.example.japritv.ui.theme.JapriTvTheme
import com.example.japritv.viewmodel.UploadEpisodeViewModel

@Composable
fun UploadVideoForm(uploadVideoViewModel: UploadEpisodeViewModel) {
    val context = LocalContext.current
    val episodes = uploadVideoViewModel.episodes

    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val file = uploadVideoViewModel.getFileFromUri(context, it)
            val fileSize = uploadVideoViewModel.getFileSize(context, it)

            val thumbnail = uploadVideoViewModel.getVideoThumbnail(context, it)

            if (file!=null) {
                uploadVideoViewModel.uploadFile(0, file, fileSize,thumbnail) // Contoh untuk episode pertama
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFFFFF))
            .padding(vertical = 30.dp, horizontal = 5.dp)
    ) {
        Column( modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()), // Aktifkan scroll
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            WarningUpload(text = "Maksimal unggah hingga 15 video dengan total ukuran file 12,94 GB")
            if (episodes.isNotEmpty()) {
                episodes.forEachIndexed { index, episode ->
                    EpisodeUploadComponent(
                        episodeIndex = index,
                        uploadVideoViewModel = uploadVideoViewModel,
                        episode = episode,
                        onClick = {
                            videoPickerLauncher.launch("video/*")
                        }
                    )
                }
            } else {
                EpisodeUploadComponent(
                    episodeIndex = 0,
                    uploadVideoViewModel = uploadVideoViewModel,
                    episode = Episode(
                        episodeTitle = "Episode 1",
                        movieTitle = "",
                        fileName = null,
                        fileSize = "",
                        isUploading = false,
                        progress = 0f,
                        thumbnail = null,
                    ),
                    onClick = {
                        videoPickerLauncher.launch("video/*")
                    }
                )
            }
           Box(modifier = Modifier.padding(horizontal = 3.dp)){
               AddEpisodeButton(onClick = {
                   // Tambahkan episode baru ke daftar
                   uploadVideoViewModel.addEpisode()
               })
           }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


//@Preview
//@Composable
//private fun UploadVideoFormPreview() {
//    JapriTvTheme {
//        UploadVideoForm()
//    }
//}