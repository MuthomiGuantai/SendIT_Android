package com.example.sendit.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sendit.api.ApiClient
import com.example.sendit.models.VerifyOtpRequest
import com.example.sendit.models.VerifyOtpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun VerifyOtpScreen(
    email: String, // Passed from RegisterScreen
    onVerificationSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var otp by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Verify OTP", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Enter the OTP sent to $email")
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = otp,
            onValueChange = { otp = it },
            label = { Text("OTP") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { verifyOtp(context, email, otp, onVerificationSuccess) }) {
            Text("Verify")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateBack) {
            Text("Back")
        }
    }
}

private fun verifyOtp(context: Context, email: String, otp: String, onVerificationSuccess: () -> Unit) {
    val api = ApiClient.getClient().create(com.example.sendit.api.SendItApi::class.java)
    val request = VerifyOtpRequest(email, otp)
    api.verifyOtp(request).enqueue(object : Callback<VerifyOtpResponse> {
        override fun onResponse(call: Call<VerifyOtpResponse>, response: Response<VerifyOtpResponse>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.success) {
                        Toast.makeText(context, "OTP verified successfully", Toast.LENGTH_SHORT).show()
                        onVerificationSuccess()
                    } else {
                        Toast.makeText(context, it.message ?: "Verification failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Verification failed: ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<VerifyOtpResponse>, t: Throwable) {
            Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}