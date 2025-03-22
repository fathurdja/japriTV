package com.example.japritv.model

data class subscriptionData(
    val _id: String,
    val userId: String,
    val level: String,
    val startDate: String,
    val endDate: String,
    val isPayed: Boolean,
    val isExpired: Boolean
)
