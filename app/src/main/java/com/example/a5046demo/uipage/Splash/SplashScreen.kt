package com.example.a5046demo.uipage.Splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a5046demo.uipage.navigation.AuthRoutes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // State to control whether the animation should start
    var startAnimation by remember { mutableStateOf(false) }

    // Animate scaling from 0 to 1 when startAnimation becomes true
    val scale = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000) // Animation duration: 1 second
    )

    // Launch animation and navigation effect once when this composable is composed
    LaunchedEffect(key1 = true) {
        startAnimation = true               // Trigger animation
        delay(2000L)                        // Wait for 2 seconds
        navController.navigate(AuthRoutes.Login) {  // Navigate to login screen
            popUpTo(AuthRoutes.Splash) { inclusive = true } // Remove splash from back stack
        }
    }

    // UI Layout: Box centers the content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Column content scales in using the animated scale value
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.scale(scale.value)
        ) {
            // App title text with large font
            Text(
                text = "🏃‍♀️FitNest 🏋️‍♀️",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp)) // Spacing between texts

            // Subtitle text
            Text(
                text = "Move freely. Live naturally.",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 18.sp
            )
        }
    }
}
