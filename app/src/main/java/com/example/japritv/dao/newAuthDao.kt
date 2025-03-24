package com.example.japritv.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth_token_New")
data class newAuthDao(
    @PrimaryKey val id: Int = 1, // Gunakan ID tetap karena hanya ada 1 token
    val token: String,
    val expiresAt: Long // Timestamp kapan token akan expired
)