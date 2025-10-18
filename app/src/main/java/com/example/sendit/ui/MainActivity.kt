// app/src/main/java/com/example/sendit/ui/MainActivity.kt

package com.example.sendit.ui

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sendit.ui.screens.DashboardScreen
import com.example.sendit.ui.screens.LoginScreen
import com.example.sendit.ui.screens.MapScreen
import com.example.sendit.ui.theme.SendITTheme
import com.example.sendit.utils.SessionManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sessionManager = SessionManager(this)
        setContent {
            SendITTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = if (sessionManager.isLoggedIn()) "dashboard" else "login") {
                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = { navController.navigate("dashboard") { popUpTo("login") { inclusive = true } } }
                            )
                        }
                        composable("dashboard") {
                            DashboardScreen(
                                onLogout = { sessionManager.logout(); navController.navigate("login") { popUpTo("dashboard") { inclusive = true } } }
                            )
                        }
                        // Add other screens as needed
                        composable("map") {
                            MapScreen(navigateBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Handle permission grant if needed
        } else {
            // Handle permission denial
        }
    }
}