package com.example.japritv


import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.japritv.Repository.VideoRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.factory.PaymentViewModelFactory
import com.example.japritv.factory.UploadEpisodeViewModelFactory
import com.example.japritv.factory.UserViewModelfactory
import com.example.japritv.factory.VideoViewModelFactory
import com.example.japritv.ui.screen.MainScreen
import com.example.japritv.viewmodel.PaymentViewModel
import com.example.japritv.viewmodel.ShowItemViewModel
import com.example.japritv.viewmodel.UploadEpisodeViewModel
import com.example.japritv.viewmodel.UserViewModel
import com.example.japritv.viewmodel.VideoViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

class  MainActivity : ComponentActivity() {

    // ✅ Pindahkan database ke level class agar tidak dibuat berulang kali
    private lateinit var videoRepository: VideoRepository
    private lateinit var database: AppDatabase

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            // Log and toast
//            Log.d("FCM", token.toString())
//            Toast.makeText(baseContext, token.toString(), Toast.LENGTH_SHORT).show()
        })
        super.onCreate(savedInstanceState)



        // ✅ Inisialisasi database hanya sekali
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "video-db")
            .fallbackToDestructiveMigration()
            .build()

        val videoDao = database.videoDao()
        videoRepository = VideoRepository(videoDao)

        setContent {
            val userViewModel: UserViewModel = viewModel(factory = UserViewModelfactory(database))
            val videoViewModel: VideoViewModel = viewModel(factory = VideoViewModelFactory(videoRepository,database))
            val data: ShowItemViewModel = viewModel()
            val paymentViewModel :PaymentViewModel = viewModel(factory = PaymentViewModelFactory(database))
            val uploadViewModel: UploadEpisodeViewModel = viewModel(factory = UploadEpisodeViewModelFactory(database))
                MainScreen(
                    data = data,
                    videoViewModel = videoViewModel,
                    uploadEpisodeViewModel = uploadViewModel,
                    userViewModel = userViewModel,
                    paymentViewModel = paymentViewModel
                )


        }
    }

    // ✅ Fungsi reset database harus berada di dalam class tetapi di luar `setContent`
//    private fun resetDatabase() {
//        deleteDatabase("video-db")
//    }
}
