package com.example.japritv.viewmodel


import androidx.lifecycle.ViewModel
import com.example.japritv.R
import com.example.japritv.model.Show


class ShowItemViewModel : ViewModel() {
    val shows = listOf(
        Show(
            title = "Breaking Bad",
            imageResId = R.drawable.title_card,
            badge = "TOP 10",
            rating = "4.9",
            genres = listOf("Drama", "Crime"),
            duration = "50m",
            popularity = "20K",
            rank = 2,
            videoResId = R.drawable.image_7,
            description = "Bercerita tentang permainan bertahan hidup yang mematikan..."
        ),
        Show(
            title = "Stranger Things",
            imageResId = R.drawable.title_card,
            badge = "TOP 10",
            rating = "4.8",
            genres = listOf("Sci-Fi", "Horror"),
            duration = "55m",
            popularity = "30K",
            rank = 1,
            videoResId = R.drawable.image_7,
            description = "Sebuah kisah misteri di kota kecil dengan elemen supernatural..."
        )
    )
}
