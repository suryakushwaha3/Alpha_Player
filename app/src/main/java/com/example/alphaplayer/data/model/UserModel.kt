package com.example.alphaplayer.data.model

data class UserModel(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val profileImage: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
