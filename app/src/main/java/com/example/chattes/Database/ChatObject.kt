package com.example.chattes.Database

data class ChatObject(
    val userId: String,
    val userName: String,
    val message: String,
    val timestamp: Long = 0
)