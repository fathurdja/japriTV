package com.example.japritv.model

data class Show(
    val title: String,
    val imageResId: Int,
    val description: String,
    val videoResId: Int,
    val badge: String? = null, // Contoh: "TOP 10", "NEW", dll.
    val rating: String? = null, // Contoh: "4.5", "PG-13", dll.
    val genres: List<String> = emptyList(), // Bisa lebih dari satu genre
    val duration: String? = null, // Contoh: "2h 10m"
    val popularity: String? = null, // Contoh: "17.8K"
    val rank: Int? = null // Peringkat di daftar (jika ada)
)


