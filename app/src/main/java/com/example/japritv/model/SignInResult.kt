package com.example.japritv.model

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val idToken: String,
    val username: String?,
    val email : String?,
    val profilePictureUrl: String?
)