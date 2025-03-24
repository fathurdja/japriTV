package com.example.japritv.model

data class subscriptionData(
    val _id: String,
    val userId: String,
    val level: String,
    val price: Int,
    val startDate: String,
    val endDate: String,
    var isPayed: Boolean,
    val isExpired: Boolean
)
