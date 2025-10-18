package com.example.sendit.models

data class LoginResponse(
    val token: String,        // JWT or session token from the backend
    val userId: String? = null // Optional user ID, adjust based on your API
)