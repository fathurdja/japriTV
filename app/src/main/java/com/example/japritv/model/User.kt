package com.example.japritv.model

data class User (
    val id: String,
    val name: String,
    val picture: String,
    val email: String,
    val phone: String,
    val coin: Long,
    val referral: String,
    val referredBy: String
)