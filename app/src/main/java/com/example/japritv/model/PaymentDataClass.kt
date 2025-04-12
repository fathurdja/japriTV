package com.example.japritv.model

data class Limit(
    val size: Long,
    val upload: Int
)

data class SubscriptionPrice(
    val mingguan: Int,
    val bulanan: Int
)

data class SubscriptionValidity(
    val mingguan: Int,
    val bulanan: Int
)

data class PaymentBank(
    val name: String,
    val online: Boolean
)
