package com.example.japritv

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.japritv.Repository.VideoRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.factory.VideoViewModelFactory
import com.example.japritv.navigation.NavGraph
import com.example.japritv.ui.screen.MainScreen
import com.example.japritv.ui.screen.SplashScreen
import com.example.japritv.ui.theme.JapriTvTheme
import com.example.japritv.viewmodel.ShowItemViewModel
import com.example.japritv.viewmodel.VideoViewModel

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
            val videoViewModel: VideoViewModel = viewModel(factory = VideoViewModelFactory(videoRepository))
            val data: ShowItemViewModel = viewModel()

            MainScreen(
                data = data,
                videoViewModel = videoViewModel,

            )
        }
    }

    // ✅ Fungsi reset database harus berada di dalam class tetapi di luar `setContent`
//    private fun resetDatabase() {
//        deleteDatabase("video-db")
//    }
}
