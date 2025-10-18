package com.example.sendit.api

import android.location.LocationRequest
import android.view.SurfaceControl
import com.example.sendit.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SendItApi {
    @POST("/api/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/api/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/api/verify-otp")
    fun verifyOtp(@Body request: VerifyOtpRequest): Call<VerifyOtpResponse>

    @POST("/api/initiate-payment")
    fun initiatePayment(@Body request: PaymentRequest): Call<PaymentResponse>

    @GET("/api/transactions")
    fun getTransactions(): Call<List<SurfaceControl.Transaction>>

    @POST("/api/submit")
    fun submitLocation(@Body request: LocationRequest): Call<Void>
}