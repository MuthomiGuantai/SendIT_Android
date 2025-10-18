package com.example.sendit.models

data class VerifyOtpRequest(
    val email: String,
    val otp: String
)