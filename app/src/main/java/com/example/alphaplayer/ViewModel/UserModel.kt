package com.example.alphaplayer.ViewModel


data class UserModel(

    val uid: String = "",

    val fullName: String = "",

    val email: String = "",

    val createdAt: Long = System.currentTimeMillis()

)