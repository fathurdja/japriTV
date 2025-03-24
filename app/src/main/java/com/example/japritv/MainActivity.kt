package com.example.japritv

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.japritv.Repository.VideoRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.factory.UploadEpisodeViewModelFactory
import com.example.japritv.factory.UserViewModelfactory
import com.example.japritv.factory.VideoViewModelFactory
import com.example.japritv.navigation.NavGraph
import com.example.japritv.ui.screen.InstruksiBayarScreen
import com.example.japritv.ui.screen.MainScreen
import com.example.japritv.ui.screen.SplashScreen
import com.example.japritv.ui.theme.JapriTvTheme
import com.example.japritv.viewmodel.PaymentViewModel
import com.example.japritv.viewmodel.ShowItemViewModel
import com.example.japritv.viewmodel.UploadEpisodeViewModel
import com.example.japritv.viewmodel.UserViewModel
import com.example.japritv.viewmodel.VideoViewModel
import kotlinx.coroutines.launch

class  MainActivity : ComponentActivity() {

    // ✅ Pindahkan database ke level class agar tidak dibuat berulang kali
    private lateinit var videoRepository: VideoRepository
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Inisialisasi database hanya sekali
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "video-db")
            .fallbackToDestructiveMigration()
            .build()

        val videoDao = database.videoDao()
        videoRepository = VideoRepository(videoDao)

        setContent {
            val userViewModel: UserViewModel = viewModel(factory = UserViewModelfactory(database))
            val videoViewModel: VideoViewModel = viewModel(factory = VideoViewModelFactory(videoRepository))
            val data: ShowItemViewModel = viewModel()

            val uploadViewModel: UploadEpisodeViewModel = viewModel(factory = UploadEpisodeViewModelFactory(database))
                MainScreen(
                    data = data,
                    videoViewModel = videoViewModel,
                    uploadEpisodeViewModel = uploadViewModel,
                    userViewModel = userViewModel
                )


        }
    }

    // ✅ Fungsi reset database harus berada di dalam class tetapi di luar `setContent`
//    private fun resetDatabase() {
//        deleteDatabase("video-db")
//    }
}
