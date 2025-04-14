package com.example.japritv.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_data")
data class PaymentDataEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val userName: String,
    val bank: String,
    val amount: Int,
    val unique: Int,
    val serverFee: Int,
    val admin: Int,
    val totalAmount: Int,
    val invoiceId: String,
    val vaNumber: String,
    val vaName: String,
    val level: String?,           // nullable karena cuma ada untuk "subscription"
    val idVideo: String?,         // nullable karena cuma ada untuk "video"
    val totalEpisode: Int? ,       // nullable karena cuma ada untuk "video",
    val timestamp: Long = System.currentTimeMillis()
)