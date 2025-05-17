package com.example.a5046demo.uipage

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.a5046demo.viewmodel.ExerciseViewModel
import com.example.a5046demo.uipage.*

@Composable
fun AppNavHost(navController: NavHostController, viewModel: ExerciseViewModel,modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier

    )
    {
        composable("home") { HomeScreen() }
        composable("record") { RecordScreen(viewModel = viewModel) }
        composable("history") { ExerciseHistoryScreen(viewModel = viewModel) }
        composable("profile") { ProfileScreen(navController = navController) } // 注意要传进去 navController
        composable("edit_profile") {
            EditProfileScreen(onBackClick = { navController.popBackStack() })
        }
    }
}