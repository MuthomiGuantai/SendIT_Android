package com.example.sendit.ui

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sendit.ui.screens.DashboardScreen
import com.example.sendit.ui.screens.LoginScreen
import com.example.sendit.ui.screens.MapScreen
import com.example.sendit.ui.screens.PaymentScreen
import com.example.sendit.ui.screens.RegisterScreen
import com.example.sendit.ui.screens.TransactionScreen
import com.example.sendit.ui.screens.VerifyOtpScreen
import com.example.sendit.ui.theme.SendITTheme
import com.example.sendit.utils.SessionManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sessionManager = SessionManager(this)
        setContent {
            SendITTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainNavigation(sessionManager = sessionManager)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Handle permission grant if needed (e.g., refresh map in MapScreen)
        } else {
            // Handle permission denial
        }
    }
}

@Composable
fun MainNavigation(sessionManager: SessionManager) {
    val navController = rememberNavController()
    val startDestination: String = if (sessionManager.isLoggedIn()) {
        "dashboard"
    } else {
        "login"
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = "login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable(route = "register") {
            RegisterScreen(navController = navController)
        }
        composable(
            route = "verify-otp?email={email}",
            arguments = listOf(navArgument(name = "email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerifyOtpScreen(
                email = email,
                onVerificationSuccess = {
                    navController.navigate("login") {
                        popUpTo("verify-otp") { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(route = "dashboard") {
            DashboardScreen(
                onLogout = {
                    sessionManager.logout()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onNavigateToMap = { navController.navigate("map") },
                onNavigateToPayment = { navController.navigate("payment") },
                onNavigateToTransactions = { navController.navigate("transactions") }
            )
        }
        composable(route = "map") {
            MapScreen(navigateBack = { navController.popBackStack() })
        }
        composable(route = "payment") {
            PaymentScreen(
                onPaymentSuccess = { navController.popBackStack() },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(route = "transactions") {
            TransactionScreen(navigateBack = { navController.popBackStack() })
        }
    }
}