package com.example.a5046demo.uipage

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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
import com.example.a5046demo.uipage.Splash.SplashScreen
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

                // ✅ 创建 ExerciseViewModel（带 userId）
                val exerciseViewModel: ExerciseViewModel = viewModel(
                    factory = ExerciseViewModelFactory(application, userId)
                )

                // ✅ 创建 UserProfileViewModel（带 userId）
                val userProfileViewModel = remember {
                    UserProfileViewModel(application, userId)
                }

                // ✅ 如果第一次登录该用户，插入默认 Profile 数据
                LaunchedEffect(userId) {
                    val dao = AppDatabase.getDatabase(application).userProfileDao()
                    val existing = dao.getUserProfileOnce(userId)
                    if (existing == null) {
                        dao.insertOrUpdate(
                            UserProfile(
                                userId = userId,
                                nickname = "New User",
                                weight = 65f,
                                height = 170f
                            )
                        )
                        Log.d("InitProfile", "Inserted default profile for $userId")
                    }
                }

                // ✅ 进入主界面（带底部导航栏）
                MainAppScaffold(
                    navController = navController,
                    viewModel = exerciseViewModel,
                    userProfileViewModel = userProfileViewModel
                )
            } else {
                // 万一 UID 拿不到（不太可能），回登录页
                AuthNavHost(navController = navController, viewModel = authViewModel)
            }
        }

        is AuthState.Loading -> {
            SplashScreen(navController = navController)
        }

        else -> {
            AuthNavHost(navController = navController, viewModel = authViewModel)
        }
    }
}

