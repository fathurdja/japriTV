package com.example.japritv.model

data class PaymentData(
    val _id: String,
    val isPayed: Boolean,
    val user: String,
    val items: dataitems,
    val amount: Int,
    val createdAt: String,
    val updatedAt: String,
    val unique: Int,
    val serverFee: Int,
    val totalAmount: Int,
    val __v: Int
)

data class dataitems(
    val _id: String,
    val totalEpisode:Int
)