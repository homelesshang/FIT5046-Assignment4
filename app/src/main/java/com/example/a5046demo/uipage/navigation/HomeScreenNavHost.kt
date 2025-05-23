package com.example.a5046demo.uipage.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.a5046demo.uipage.*
import com.example.a5046demo.viewmodel.AuthViewModel
import com.example.a5046demo.viewmodel.ExerciseViewModel
import com.example.a5046demo.viewmodel.UserProfileViewModel

// Define route names for the main (home) navigation graph
object HomePageRoutes {
    const val Home = "home"                // Home screen
    const val Record = "record"            // Record workout screen
    const val History = "history"          // Exercise history screen
    const val Profile = "profile"          // User profile screen
    const val EditProfile = "edit_profile" // Edit profile screen
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,                  // ViewModel for authentication
    viewModel: ExerciseViewModel,                  // ViewModel for exercise-related data
    userProfileViewModel: UserProfileViewModel,    // ViewModel for user profile
    modifier: Modifier = Modifier                  // Optional modifier for customization
) {
    // Define the navigation graph with Home as the start screen
    NavHost(
        navController = navController,
        startDestination = HomePageRoutes.Home,
        modifier = modifier
    ) {
        // Home screen composable route
        composable(HomePageRoutes.Home) {
            HomeScreen(
                viewModel = userProfileViewModel,
                exerciseViewModel = viewModel,
                navController = navController
            )
        }

        // Record workout screen
        composable(HomePageRoutes.Record) {
            RecordScreen(viewModel = viewModel)
        }

        // Exercise history screen
        composable(HomePageRoutes.History) {
            ExerciseHistoryScreen(viewModel = viewModel, navController = navController)
        }

        // Progress screen (shows weekly summary statistics)
        composable("progress") {
            GreenStatsPageWithHeader(
                viewModel = viewModel,
                userViewModel = userProfileViewModel,
                onClose = { navController.popBackStack() } // Go back when closed
            )
        }

        // Profile screen (account and settings)
        composable(HomePageRoutes.Profile) {
            ProfileScreen(
                navController = navController,
                authViewModel = authViewModel,
                userProfileViewModel = userProfileViewModel,
                exerciseViewModel = viewModel
            )
        }

        // Edit profile screen
        composable(HomePageRoutes.EditProfile) {
            EditProfileScreen(
                userProfileViewModel = userProfileViewModel,
                onBackClick = { navController.popBackStack() } // Navigate back
            )
        }
    }
}
