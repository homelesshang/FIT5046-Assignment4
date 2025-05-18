package com.example.a5046demo.uipage.navigation


import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


import com.example.a5046demo.uipage.Splash.SplashScreen
import com.example.a5046demo.uipage.auth.LoginScreen
import com.example.a5046demo.uipage.auth.RegisterScreen
import com.example.a5046demo.viewmodel.AuthViewModel

object AuthRoutes {
    const val Splash = "splash"
    const val Login = "login"
    const val Register = "register"
}
@Composable
fun AuthNavHost(navController: NavHostController, viewModel: AuthViewModel) {
    NavHost(navController = navController, startDestination = AuthRoutes.Splash) {
        composable(AuthRoutes.Splash) {
            SplashScreen(navController = navController)
        }
        composable(AuthRoutes.Login) {
            LoginScreen(navController = navController, viewModel = viewModel)
        }
        composable(AuthRoutes.Register) {
            RegisterScreen(navController = navController, viewModel = viewModel)
        }
    }
}