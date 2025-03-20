package com.example.japritv.dao

import android.graphics.Picture
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_info")
data class LoginInfo(
    @PrimaryKey val id: Int = 1, // Selalu hanya ada satu token
    val tokenAuth: String,
    val name: String,
    val urlPicture:String,
    val infoRegistrasi: String
)