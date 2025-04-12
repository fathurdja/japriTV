package com.example.japritv.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.japritv.dao.VideoData
import com.example.japritv.dao.historyEntity
import com.example.japritv.ui.components.home.MovieItem

@Composable
fun ShowHistorySection(shows: List<historyEntity>, onClick: (String,Int) -> Unit) {
    // Memeriksa apakah daftar 'shows' kosong
    if (shows.isEmpty()) {
        // Menampilkan CircularProgressIndicator saat data kosong atau sedang dimuat
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = Color.White
            )
        }
    } else {
        // Menampilkan grid saat data sudah tersedia
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(shows) { show ->
                val title = show.title ?: "No Title"
                MovieItemHistory(show, title, onClick ={
                    onClick(show.idGroup, show.episode)
                } )
            }
        }
    }
}