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
import com.example.sendit.models.PaymentRequest
import com.example.sendit.models.PaymentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun PaymentScreen(onPaymentSuccess: () -> Unit, onNavigateBack: () -> Unit) {
    var amount by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { initiatePayment(context, amount, onPaymentSuccess) }) {
            Text("Pay Now")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateBack) {
            Text("Back")
        }
    }
}

private fun initiatePayment(context: Context, amount: String, onPaymentSuccess: () -> Unit) {
    val api = ApiClient.getClient().create(com.example.sendit.api.SendItApi::class.java)
    val request = PaymentRequest(amount.toDoubleOrNull() ?: 0.0) // Assume amount is a Double
    api.initiatePayment(request).enqueue(object : Callback<PaymentResponse> {
        override fun onResponse(call: Call<PaymentResponse>, response: Response<PaymentResponse>) {
            if (response.isSuccessful) {
                Toast.makeText(context, "Payment initiated", Toast.LENGTH_SHORT).show()
                onPaymentSuccess()
            } else {
                Toast.makeText(context, "Payment failed: ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
            Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}