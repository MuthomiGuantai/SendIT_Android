package com.example.sendit.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.sendit.api.ApiClient
import com.example.sendit.models.Transaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun TransactionScreen(navigateBack: () -> Unit) {
    var transactions by remember { mutableStateOf<List<Transaction>>(emptyList()) }
    val context = LocalContext.current

    // Fetch transactions on compose
    LaunchedEffect(Unit) {
        val api = ApiClient.getClient().create(com.example.sendit.api.SendItApi::class.java)
        api.getTransactions().enqueue(object : Callback<List<Transaction>> { // Explicitly typed
            override fun onResponse(call: Call<List<Transaction>>, response: Response<List<Transaction>>) {
                if (response.isSuccessful) {
                    response.body()?.let { transactions = it }
                } else {
                    Toast.makeText(context, "Failed to load transactions: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Transaction>>, t: Throwable) {
                Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Transaction History", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(transactions) { transaction ->
                Text("Transaction ID: ${transaction.id}, Amount: ${transaction.amount}", modifier = Modifier.padding(vertical = 8.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = navigateBack) {
            Text("Back")
        }
    }
}