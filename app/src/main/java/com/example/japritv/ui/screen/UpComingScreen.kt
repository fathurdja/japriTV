package com.example.japritv.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.japritv.R
import com.example.japritv.model.Show
import com.example.japritv.ui.components.home.UpComingCard

@Composable
fun UpComingScreen(modifier: Modifier = Modifier, shows: List<Show>,navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(shows.size) { show ->
                val show = shows[show]
                Box(
                ) {
                    UpComingCard(  title = "Money Heist: Korea. Joint Economic Area",
                        description = "Bercerita tentang permainan bertahan hidup yang mematikan. Serial ini mengisahkan tentang 456 orang yang berpartisipasi dalam permainan untuk memenangkan hadiah uang tunai.",
                        Video = R.drawable.image_7,
                        Poster = R.drawable.title_card,
                        time = "01:47" ,
                        onReminderClick = {})
                }
            }
        }
    }
}