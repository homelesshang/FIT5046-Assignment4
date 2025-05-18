package com.example.a5046demo.uipage.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.a5046demo.uipage.EditProfileScreen
import com.example.a5046demo.uipage.ExerciseHistoryScreen
import com.example.a5046demo.uipage.HomeScreen
import com.example.a5046demo.uipage.ProfileScreen
import com.example.a5046demo.uipage.RecordScreen
import com.example.a5046demo.viewmodel.ExerciseViewModel


object HomePageRoutes {
    const val Home = "home"
    const val Record = "record"
    const val History = "history"
    const val Profile = "profile"
    const val EditProfile = "edit_profile"
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    viewModel: ExerciseViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomePageRoutes.Home,
        modifier = modifier
    ) {
        composable(HomePageRoutes.Home) {
            HomeScreen()
        }
        composable(HomePageRoutes.Record) {
            RecordScreen(viewModel = viewModel)
        }
        composable(HomePageRoutes.History) {
            ExerciseHistoryScreen(viewModel = viewModel)
        }
        composable(HomePageRoutes.Profile) {
            ProfileScreen(navController = navController)
        }
        composable(HomePageRoutes.EditProfile) {
            EditProfileScreen(onBackClick = { navController.popBackStack() })
        }
    }
}