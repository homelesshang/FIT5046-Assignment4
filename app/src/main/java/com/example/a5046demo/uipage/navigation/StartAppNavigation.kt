package com.example.a5046demo.uipage

import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.navigation.compose.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a5046demo.uipage.navigation.AuthNavHost
import com.example.a5046demo.viewmodel.AuthState
import com.example.a5046demo.viewmodel.AuthViewModel
import com.example.a5046demo.viewmodel.ExerciseViewModel
import com.example.a5046demo.uipage.Splash.SplashScreen


@Composable
fun StartAppNavigation(viewModel: ExerciseViewModel) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.provideFactory(context))
    val authState by authViewModel.authState.collectAsState()

    val navController = rememberNavController()

    when (authState) {
        is AuthState.Success -> {
            MainAppScaffold(navController = navController, viewModel = viewModel)
        }

        is AuthState.Loading -> {
            SplashScreen(navController = navController)
        }

        else -> {
            AuthNavHost(navController = navController, viewModel = authViewModel)
        }
    }
}


