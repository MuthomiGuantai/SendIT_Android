package com.example.sendit.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(onLogout: () -> Unit,
                    onNavigateToMap: () -> Unit,
                    onNavigateToPayment: () -> Unit,
                    onNavigateToTransactions: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Dashboard")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToMap) {
            Text("Select Location")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateToPayment) {
            Text("Make Payment")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateToTransactions) {
            Text("View Transactions")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}