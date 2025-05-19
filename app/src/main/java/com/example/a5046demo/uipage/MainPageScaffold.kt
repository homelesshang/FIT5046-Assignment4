package com.example.a5046demo.uipage


import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.a5046demo.viewmodel.ExerciseViewModel
import com.example.a5046demo.uipage.navigation.MainNavHost
import com.example.a5046demo.viewmodel.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun MainAppScaffold(navController: NavHostController, viewModel: ExerciseViewModel,  userProfileViewModel: UserProfileViewModel ) {

    data class NavRoute(val route: String, val icon: ImageVector, val label: String)

    val navRoutes = listOf(
        NavRoute("home", Icons.Filled.Home, "Home"),
        NavRoute("record", Icons.Filled.Edit, "Record"),
        NavRoute("history", Icons.Filled.List, "History"),
        NavRoute("profile", Icons.Filled.Person, "Profile")
    )

    val navController = rememberNavController()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val context = LocalContext.current.applicationContext as Application
    val userProfileViewModel = remember {
        UserProfileViewModel(context, userId)
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color(0xFF2E8B57)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                navRoutes.forEach { navRoute ->
                    NavigationBarItem(
                        icon = { Icon(navRoute.icon, contentDescription = navRoute.label) },
                        label = { Text(navRoute.label) },
                        selected = currentRoute == navRoute.route,
                        onClick = {
                            navController.navigate(navRoute.route) {
                                popUpTo("home") { inclusive = false }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
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
        MainNavHost(
            navController = navController,
            viewModel = viewModel,
            userProfileViewModel = userProfileViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}