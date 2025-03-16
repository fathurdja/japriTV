package com.example.japritv.ui.components.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.japritv.model.DataItem
import com.example.japritv.model.Show

@Composable
fun ShowsGridSection(shows:List<DataItem>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),

        contentPadding = PaddingValues(8.dp)
    ) {
        items(shows) { show ->
            MovieItem(show, show.title)
        }
    }
}