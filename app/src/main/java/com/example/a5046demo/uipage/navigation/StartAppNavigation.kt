package com.example.a5046demo.uipage

import android.app.Application
import android.util.Log
import androidx.compose.runtime.*

import androidx.navigation.compose.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a5046demo.data.AppDatabase
import com.example.a5046demo.data.UserProfile
import com.example.a5046demo.uipage.navigation.AuthNavHost
import com.example.a5046demo.viewmodel.AuthState
import com.example.a5046demo.viewmodel.AuthViewModel
import com.example.a5046demo.viewmodel.ExerciseViewModel
import com.example.a5046demo.viewmodel.ExerciseViewModelFactory
import com.example.a5046demo.viewmodel.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun StartAppNavigation() {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()
    val authState by authViewModel.authState.collectAsState()
    val navController = rememberNavController()

    when (authState) {
        is AuthState.Success -> {
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            if (userId != null) {
                val application = context.applicationContext as Application


                val exerciseViewModel: ExerciseViewModel = viewModel(
                    key = userId,
                    factory = ExerciseViewModelFactory(application, userId)
                )


                val userProfileViewModel: UserProfileViewModel = remember(userId) {
                    UserProfileViewModel(application, userId)
                }



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



                MainAppScaffold(
                    navController = navController,
                    authViewModel = authViewModel,
                    viewModel = exerciseViewModel,
                    userProfileViewModel = userProfileViewModel
                )
            } else {
                AuthNavHost(navController = navController, viewModel = authViewModel)
            }
        }

//        is AuthState.Loading -> {
//            AuthNavHost(navController = navController, viewModel = authViewModel)
//        }

        else -> {
            AuthNavHost(navController = navController, viewModel = authViewModel)
        }
    }
}

