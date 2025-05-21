package com.example.a5046demo.uipage.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.a5046demo.uipage.EditProfileScreen
import com.example.a5046demo.uipage.ExerciseHistoryScreen
import com.example.a5046demo.uipage.GreenStatsPageWithHeader
import com.example.a5046demo.uipage.HomeScreen
import com.example.a5046demo.uipage.ProfileScreen
import com.example.a5046demo.uipage.RecordScreen
import com.example.a5046demo.viewmodel.AuthViewModel
import com.example.a5046demo.viewmodel.ExerciseViewModel
import com.example.a5046demo.viewmodel.UserProfileViewModel


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
    authViewModel:AuthViewModel,
    viewModel: ExerciseViewModel,
    userProfileViewModel: UserProfileViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomePageRoutes.Home,
        modifier = modifier
    ) {
        composable(HomePageRoutes.Home) {
            HomeScreen(viewModel = userProfileViewModel,exerciseViewModel = viewModel,navController = navController)
        }
        composable(HomePageRoutes.Record) {
            RecordScreen(viewModel = viewModel)
        }
        composable(HomePageRoutes.History) {
            ExerciseHistoryScreen(viewModel = viewModel, navController = navController)
        }
        composable("progress") {
            GreenStatsPageWithHeader(viewModel = viewModel, onClose = { navController.popBackStack() }) // workout weekly summary
        }
        composable(HomePageRoutes.Profile) {
            ProfileScreen(navController = navController, authViewModel = authViewModel, userProfileViewModel = userProfileViewModel)
        }
        composable(HomePageRoutes.EditProfile) {
            EditProfileScreen(userProfileViewModel = userProfileViewModel,onBackClick = { navController.popBackStack() })
        }
    }
}

