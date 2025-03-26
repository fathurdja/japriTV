package com.example.japritv.model

data class GoogleAccount(
    val token: String,
    val displayName: String = "",
    val profileImageUrl: String? = null,
    val authCode: String? = null
)