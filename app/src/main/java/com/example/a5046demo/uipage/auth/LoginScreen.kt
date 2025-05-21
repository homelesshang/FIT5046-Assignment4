package com.example.a5046demo.uipage.auth

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.a5046demo.components.*
import com.example.a5046demo.uipage.*
import com.example.a5046demo.uipage.navigation.AuthRoutes
import com.example.a5046demo.uipage.navigation.HomePageRoutes
import com.example.a5046demo.viewmodel.AuthState
import com.example.a5046demo.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes

private const val TAG = "LoginScreen"

@Composable
fun LoginScreen(


    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {

    var Email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()


    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()

    ) { result ->
        Log.d(TAG, "Google Sign In result received: resultCode=${result.resultCode}, data=${result.data}")
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                try {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    Log.d(TAG, "Processing Google Sign In result...")
                    val account = task.getResult(ApiException::class.java)
                    account?.idToken?.let { idToken ->
                        Log.d(TAG, "Got Google ID Token: $idToken")
                        viewModel.signInWithGoogle(idToken)
                    } ?: run {
                        showError = "Failed to get ID token from Google account"
                        Log.e(TAG, "No ID token found in Google account")
                    }
                } catch (e: ApiException) {
                    val errorMessage = when (e.statusCode) {
                        GoogleSignInStatusCodes.SIGN_IN_CANCELLED -> {
                            Log.e(TAG, "Sign in cancelled by ApiException: ${e.statusCode}", e)
                            "Sign-in was cancelled"
                        }
                        GoogleSignInStatusCodes.NETWORK_ERROR -> {
                            Log.e(TAG, "Network error during sign in: ${e.statusCode}", e)
                            "Network error occurred. Please check your connection"
                        }
                        GoogleSignInStatusCodes.INVALID_ACCOUNT -> {
                            Log.e(TAG, "Invalid account: ${e.statusCode}", e)
                            "Invalid Google account"
                        }
                        GoogleSignInStatusCodes.SIGN_IN_REQUIRED -> {
                            Log.e(TAG, "Sign in required: ${e.statusCode}", e)
                            "Please sign in to your Google account"
                        }
                        else -> {
                            Log.e(TAG, "Unknown error during sign in: ${e.statusCode}", e)
                            "Sign-in failed (code: ${e.statusCode})"
                        }
                    }
                    showError = errorMessage
                } catch (e: Exception) {
                    Log.e(TAG, "Unexpected error during Google sign in", e)
                    showError = "Unexpected error during sign-in: ${e.message}"
                }
            }
            Activity.RESULT_CANCELED -> {
                Log.d(TAG, "Google Sign In explicitly cancelled by user or system")
                showError = "Sign-in was cancelled"
            }
            else -> {
                Log.e(TAG, "Unexpected result code from Google Sign In: ${result.resultCode}")
                showError = "Unexpected error during sign-in"
            }
        }
    }

    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthState.Success -> {

                navController.navigate(HomePageRoutes.Home) {
                    popUpTo(AuthRoutes.Login) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                showError = (authState as AuthState.Error).message
                Log.e(TAG, "Authentication error: $showError")
            }
            is AuthState.Loading -> {
                Log.d(TAG, "Authentication in progress...")
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome Back!",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        FitNestTextField(
            value = Email,
            onValueChange = {
                Email = it
                showError = null
            },
            label = "Email",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        FitNestTextField(
            value = password,
            onValueChange = {
                password = it
                showError = null
            },
            label = "Password",
            isPassword = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )

        if (showError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = showError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        FitNestButton(
            onClick = {
                viewModel.signInWithEmailPassword(Email, password)
            },
            text = if (authState is AuthState.Loading) "Signing in..." else "Login",
            enabled = Email.isNotBlank() &&
                    password.isNotBlank() &&
                    authState !is AuthState.Loading
        )

        Spacer(modifier = Modifier.height(16.dp))

        FitNestOutlinedButton(
            onClick = {
                try {
                    Log.d(TAG, "Starting Google Sign In...")
                    // Check if there's an existing Google sign-in
                    val lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(context)
                    if (lastSignedInAccount != null) {
                        Log.d(TAG, "Found existing Google sign-in, signing out first")
                        val signInClient = viewModel.getGoogleSignInClient(context)
                        signInClient.signOut().addOnCompleteListener {
                            Log.d(TAG, "Previous Google sign-in cleared")
                            googleSignInLauncher.launch(signInClient.signInIntent)
                        }
                    } else {
                        val signInClient = viewModel.getGoogleSignInClient(context)
                        googleSignInLauncher.launch(signInClient.signInIntent)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error launching Google Sign In", e)
                    showError = "Error starting sign-in process"
                }
            },
            text = "Sign in with Google",
            enabled = authState !is AuthState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Google Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Sign in with Google")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { navController.navigate(AuthRoutes.Register) },
            enabled = authState !is AuthState.Loading
        ) {
            Text(
                text = "Don't have an account? Register",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
} 