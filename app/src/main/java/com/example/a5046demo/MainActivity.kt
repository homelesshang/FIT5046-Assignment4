package com.example.a5046demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a5046demo.ui.theme._5046demoTheme
import com.example.a5046demo.uipage.WeatherCard
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import com.example.a5046demo.uipage.*
import android.util.Log
import com.example.a5046demo.viewmodel.ExerciseViewModel
import com.example.a5046demo.viewmodel.ExerciseViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth

// Main entry point of the application
class MainActivity : ComponentActivity() {

    // Lifecycle callback when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables edge-to-edge content drawing
        enableEdgeToEdge()

        // Sets the content of the activity using Jetpack Compose
        setContent {
            // Applies the custom MaterialTheme to the whole app
            _5046demoTheme {
                // Surface provides a background for the UI
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Launches the navigation system and routes
                    StartAppNavigation()
                }
            }
        }
    }
}
