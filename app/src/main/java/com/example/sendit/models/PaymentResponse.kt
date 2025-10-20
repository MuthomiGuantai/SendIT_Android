package com.example.sendit.models

data class PaymentResponse(
    val message: String,
    val transactionId: String? = null
)