package com.example.a5046demo.uipage.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.a5046demo.uipage.Splash.SplashScreen
import com.example.a5046demo.uipage.auth.LoginScreen
import com.example.a5046demo.uipage.auth.RegisterScreen
import com.example.a5046demo.viewmodel.AuthViewModel

// Define navigation route names as constants for consistency and type safety
object AuthRoutes {
    const val Splash = "splash"    // Splash screen route
    const val Login = "login"      // Login screen route
    const val Register = "register"// Register screen route
}

@Composable
fun AuthNavHost(navController: NavHostController, viewModel: AuthViewModel) {
    // Define the navigation graph for the authentication flow
    NavHost(
        navController = navController,             // NavController handles navigation actions
        startDestination = AuthRoutes.Splash       // First screen to display is the splash screen
    ) {
        // Splash screen composable route
        composable(AuthRoutes.Splash) {
            SplashScreen(navController = navController)
        }

        // Login screen composable route
        composable(AuthRoutes.Login) {
            LoginScreen(navController = navController, viewModel = viewModel)
        }

        // Register screen composable route
        composable(AuthRoutes.Register) {
            RegisterScreen(navController = navController, viewModel = viewModel)
        }
    }
}
