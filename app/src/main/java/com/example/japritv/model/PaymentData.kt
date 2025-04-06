package com.example.japritv.model

import org.json.JSONObject

data class PaymentData(
    val id: String,
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
    val vaName: String
)
