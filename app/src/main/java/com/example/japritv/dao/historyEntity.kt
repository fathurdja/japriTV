package com.example.japritv.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class historyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idGroup: String,
    val title: String,
    val episode: Int,
    val poster: String,
    val idVideo: String,
    val timestamp: Long = System.currentTimeMillis()
)