package com.example.a5046demo.uipage

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.a5046demo.viewmodel.ExerciseViewModel
import com.example.a5046demo.uipage.navigation.MainNavHost
import com.example.a5046demo.viewmodel.AuthViewModel
import com.example.a5046demo.viewmodel.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainAppScaffold(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    viewModel: ExerciseViewModel,
    userProfileViewModel: UserProfileViewModel
) {

    // Define a data class to represent each navigation tab item
    data class NavRoute(val route: String, val icon: ImageVector, val label: String)

    // List of routes for the bottom navigation bar
    val navRoutes = listOf(
        NavRoute("home", Icons.Filled.Home, "Home"),
        NavRoute("record", Icons.Filled.Edit, "Record"),
        NavRoute("history", Icons.Filled.List, "History"),
        NavRoute("profile", Icons.Filled.Person, "Profile")
    )

    // Create a NavController to handle navigation events
    val navController = rememberNavController()

    // Get the current Firebase user ID
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    // Get the application context to pass to the view model
    val context = LocalContext.current.applicationContext as Application

    // Create or reuse the UserProfileViewModel
    val userProfileViewModel = remember {
        UserProfileViewModel(context, userId)
    }

    // Define the layout structure using Scaffold
    Scaffold(
        // Bottom navigation bar
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color(0xFF2E8B57)
            ) {
                // Observe the current route from the nav controller
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // Render each NavigationBarItem
                navRoutes.forEach { navRoute ->
                    NavigationBarItem(
                        icon = { Icon(navRoute.icon, contentDescription = navRoute.label) },
                        label = { Text(navRoute.label) },
                        selected = currentRoute == navRoute.route,
                        onClick = {
                            // Navigate to the selected route
                            navController.navigate(navRoute.route) {
                                popUpTo("home") { inclusive = false }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        // Define visual styling for selected/unselected states
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            indicatorColor = Color(0xFF2E8B57),
                            unselectedIconColor = Color(0xFF888888),
                            unselectedTextColor = Color(0xFF888888)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        // Load the main navigation graph and apply padding from Scaffold
        MainNavHost(
            navController = navController,
            authViewModel = authViewModel,
            viewModel = viewModel,
            userProfileViewModel = userProfileViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}