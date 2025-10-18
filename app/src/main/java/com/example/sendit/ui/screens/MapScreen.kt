// app/src/main/java/com/example/sendit/ui/screens/MapScreen.kt

package com.example.sendit.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.example.sendit.api.ApiClient
import com.example.sendit.api.SendItApi
import com.example.sendit.models.LocationRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.app.ActivityCompat
import com.google.maps.android.compose.MarkerState

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapScreen(navigateBack: () -> Unit) {
    // Use remember to persist state across recompositions
    var latitude by remember { mutableStateOf(0.0) }
    var longitude by remember { mutableStateOf(0.0) }
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-1.286389, 36.817223), 15f) // Default: Nairobi
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = LatLng(latitude, longitude)),
                title = "Current Location"
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            location?.let {
                                latitude = it.latitude
                                longitude = it.longitude
                                cameraPositionState.position = CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), 15f)
                            } ?: Toast.makeText(context, "Location not available", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        ActivityCompat.requestPermissions(
                            context as androidx.activity.ComponentActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            1001
                        )
                    }
                }
            ) {
                Text("Get Current Location")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        val locationRequest = com.google.android.gms.location.LocationRequest.create() // Should be android.location.LocationRequest
                            .setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setInterval(10000)
                        fusedLocationClient.requestLocationUpdates(locationRequest, object : com.google.android.gms.location.LocationCallback() {
                            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                                locationResult.lastLocation?.let {
                                    latitude = it.latitude
                                    longitude = it.longitude
                                    cameraPositionState.position = CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), 15f)
                                }
                            }
                        }, Looper.getMainLooper())
                    } else {
                        ActivityCompat.requestPermissions(
                            context as androidx.activity.ComponentActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            1001
                        )
                    }
                }
            ) {
                Text("Get Current Location")
            }
        }
    }
}