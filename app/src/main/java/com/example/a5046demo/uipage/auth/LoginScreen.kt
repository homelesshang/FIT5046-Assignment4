// LoginScreen handles both email/password and Google-based authentication
// It uses AuthViewModel to manage state and navigate on success

package com.example.a5046demo.uipage.auth

import android.app.Activity
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.a5046demo.R
import com.example.a5046demo.components.*
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
    // State variables for form inputs and error display
    var Email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()

    // Google Sign In launcher setup
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle result from Google Sign In
        Log.d(TAG, "Google Sign In result received: resultCode=${result.resultCode}, data=${result.data}")
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                try {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    val account = task.getResult(ApiException::class.java)
                    account?.idToken?.let { idToken ->
                        viewModel.signInWithGoogle(idToken)
                    } ?: run {
                        showError = "Failed to get ID token from Google account"
                    }
                } catch (e: ApiException) {
                    showError = when (e.statusCode) {
                        GoogleSignInStatusCodes.SIGN_IN_CANCELLED -> "Sign-in was cancelled"
                        GoogleSignInStatusCodes.NETWORK_ERROR -> "Network error occurred. Please check your connection"
                        GoogleSignInStatusCodes.INVALID_ACCOUNT -> "Invalid Google account"
                        GoogleSignInStatusCodes.SIGN_IN_REQUIRED -> "Please sign in to your Google account"
                        else -> "Sign-in failed (code: ${e.statusCode})"
                    }
                } catch (e: Exception) {
                    showError = "Unexpected error during sign-in: ${e.message}"
                }
            }
            Activity.RESULT_CANCELED -> {
                showError = "Sign-in was cancelled"
            }
            else -> {
                showError = "Unexpected error during sign-in"
            }
        }
    }

    // Navigate to home on successful auth
    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthState.Success -> {
                navController.navigate(HomePageRoutes.Home) {
                    popUpTo(AuthRoutes.Login) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                showError = state.message
            }
            else -> {}
        }
    }

    // UI layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome Back!", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(32.dp))

        // Email field
        FitNestTextField(
            value = Email,
            onValueChange = {
                Email = it
                showError = null
            },
            label = "Email",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password field
        FitNestTextField(
            value = password,
            onValueChange = {
                password = it
                showError = null
            },
            label = "Password",
            isPassword = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
        )

        // Show error if exists
        if (showError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(showError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Login button
        FitNestButton(
            onClick = { viewModel.signInWithEmailPassword(Email, password) },
            text = if (authState is AuthState.Loading) "Signing in..." else "Login",
            enabled = Email.isNotBlank() && password.isNotBlank() && authState !is AuthState.Loading
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Google Sign-In button
        FitNestOutlinedButton(
            onClick = {
                try {
                    val lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(context)
                    val signInClient = viewModel.getGoogleSignInClient(context)
                    if (lastSignedInAccount != null) {
                        signInClient.signOut().addOnCompleteListener {
                            googleSignInLauncher.launch(signInClient.signInIntent)
                        }
                    } else {
                        googleSignInLauncher.launch(signInClient.signInIntent)
                    }
                } catch (e: Exception) {
                    showError = "Error starting sign-in process"
                }
            },
            text = "Sign in with Google",
            enabled = authState !is AuthState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(painter = painterResource(id = R.drawable.icongoogle), contentDescription = "Google Icon", modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sign in with Google")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigate to register screen
        TextButton(
            onClick = { navController.navigate(AuthRoutes.Register) },
            enabled = authState !is AuthState.Loading
        ) {
            Text("Don't have an account? Register", color = MaterialTheme.colorScheme.primary)
        }
    }
}