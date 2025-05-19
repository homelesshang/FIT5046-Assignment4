package com.example.a5046demo.uipage.auth

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.a5046demo.components.*
import com.example.a5046demo.uipage.*
import com.example.a5046demo.viewmodel.AuthState
import com.example.a5046demo.viewmodel.AuthViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.ui.platform.LocalContext
import com.example.a5046demo.uipage.navigation.AuthRoutes
import com.example.a5046demo.uipage.navigation.HomePageRoutes

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var passwordError by remember {                      mutableStateOf<String?>(null) }
    var showError by remember { mutableStateOf<String?>(null) }

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val datePickerState = rememberDatePickerState()
    val authState by viewModel.authState.collectAsState()

    // Password validation
    LaunchedEffect(password, confirmPassword) {
        passwordError = when {
            password.length < 6 -> "Password must be at least 6 characters"
            password != confirmPassword && confirmPassword.isNotEmpty() -> "Passwords do not match"
            else -> null
        }
    }
    val context = LocalContext.current
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {


                navController.navigate(HomePageRoutes.Home) {
                    popUpTo(AuthRoutes.Register) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                showError = (authState as AuthState.Error).message
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
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        FitNestTextField(
            value = email,
            onValueChange = {
                email = it
                showError = null
            },
            label = "email",
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
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        FitNestTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                showError = null
            },
            label = "Confirm Password",
            isPassword = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        )

        if (passwordError != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = passwordError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (showError != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = showError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = { },
            label = { Text("Date of Birth") },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val localDate = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                dateOfBirth = localDate.format(dateFormatter)
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = false
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        FitNestButton(
            onClick = {
                viewModel.register(email, password, dateOfBirth)
            },
            text = if (authState is AuthState.Loading) "Registering..." else "Register",
            enabled = email.isNotBlank() &&
                    password.isNotBlank() &&
                    confirmPassword.isNotBlank() &&
                    passwordError == null &&
                    dateOfBirth.isNotBlank() &&
                    authState !is AuthState.Loading
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { navController.navigate(AuthRoutes.Login) },
            enabled = authState !is AuthState.Loading
        ) {
            Text(
                text = "Already have an account? Login",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}