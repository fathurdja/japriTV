package com.example.japritv.ui.components.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.japritv.model.Show

@Composable
fun SearchResultList(shows: List<Show>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        // Menggunakan items untuk menampilkan list, pastikan ada key yang unik
        items(shows, key = { show -> show.title }) { show ->
            // Menampilkan CardRating hanya jika rank dan popularity ada
            show.rank?.let { rank ->
                show.popularity?.let { popularity ->
                    Box(modifier = Modifier.padding(7.dp)) {
                        CardRating(
                            rank = rank,
                            show = show,
                            popularity = popularity
                        )
                    }
                }
            }
        }
    }
}
