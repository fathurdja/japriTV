package com.example.japritv.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth_token")
data class AuthToken(
    @PrimaryKey val id: Int = 1, // Selalu hanya ada satu token
    val token: String
)