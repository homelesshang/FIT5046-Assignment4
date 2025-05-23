package com.example.a5046demo.uipage

import android.app.Application
import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a5046demo.data.AppDatabase
import com.example.a5046demo.data.UserProfile
import com.example.a5046demo.uipage.navigation.AuthNavHost
import com.example.a5046demo.viewmodel.*
import com.google.firebase.auth.FirebaseAuth

/**
 * StartAppNavigation is the root navigation entry point of the app.
 * It decides which navigation flow to load depending on whether the user is authenticated.
 */
@Composable
fun StartAppNavigation() {
    val context = LocalContext.current

    // ViewModel managing authentication state (Firebase-based)
    val authViewModel: AuthViewModel = viewModel()

    // Observing authentication state
    val authState by authViewModel.authState.collectAsState()

    // Navigation controller to drive screen transitions
    val navController = rememberNavController()

    // When user is authenticated (via email or Google)
    when (authState) {
        is AuthState.Success -> {
            // Get Firebase user ID
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            if (userId != null) {
                val application = context.applicationContext as Application

                // Initialize ExerciseViewModel with userId (tracks workouts)
                val exerciseViewModel: ExerciseViewModel = viewModel(
                    key = userId,
                    factory = ExerciseViewModelFactory(application, userId)
                )

                // Initialize UserProfileViewModel to manage user data like nickname, height, etc.
                val userProfileViewModel: UserProfileViewModel = remember(userId) {
                    UserProfileViewModel(application, userId)
                }

                // Sync user profile from Firebase, or insert a default if not found
                LaunchedEffect(userId) {
                    val synced = userProfileViewModel.syncUserProfileFromFirebase()
                    if (!synced) {
                        userProfileViewModel.insertLocalProfile(
                            UserProfile(
                                userId = userId,
                                nickname = "New User",
                                weight = null,
                                height = null,
                                birthday = null
                            )
                        )
                        Log.d("InitProfile", "Inserted default profile for $userId")
                    } else {
                        Log.d("InitProfile", "Fetched and saved profile from Firebase")
                    }
                }

                // Launch the main application scaffold with authenticated user context
                MainAppScaffold(
                    navController = navController,
                    authViewModel = authViewModel,
                    viewModel = exerciseViewModel,
                    userProfileViewModel = userProfileViewModel
                )
            } else {
                // In case user ID is null, fallback to login flow
                AuthNavHost(navController = navController, viewModel = authViewModel)
            }
        }

        // In unauthenticated or initial state, show authentication screens
        else -> {
            AuthNavHost(navController = navController, viewModel = authViewModel)
        }
    }
}
