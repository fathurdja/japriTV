package com.example.japritv.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.japritv.model.Show
import com.example.japritv.ui.components.home.CardRating


@Composable
fun RatingScreen(shows: List<Show>, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(shows.size) { show ->
                val show = shows[show]
                Box(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .padding(2.dp)
                ) {
                    CardRating(
                        rank = show.rank ?: 0,
                        show = show,
                        popularity = show.popularity ?: "0"
                    )

                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


