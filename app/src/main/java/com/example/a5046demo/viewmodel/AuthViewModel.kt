package com.example.a5046demo.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a5046demo.data.AppDatabase
import com.example.a5046demo.data.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "AuthViewModel"

sealed class AuthState {
    object Initial : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState

    // Simulated user storage
    private val users = mutableMapOf<String, String>()

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestId()
            .requestIdToken("228885919086-9kt6e790ekk53bgsedqrq5sjkppcnuh4.apps.googleusercontent.com")
            .build()

        Log.d(TAG, "Configuring Google Sign In with options: $gso")
        val client = GoogleSignIn.getClient(context, gso)
        // Clear any previous sign-in state
        client.signOut()
        return client
    }

    fun signInWithGoogle(email: String) {
        Log.d(TAG, "Attempting Google Sign In with email: $email")
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                repository.loginWithGoogle(email).fold(
                    onSuccess = {
                        Log.d(TAG, "Google Sign In successful")
                        _authState.value = AuthState.Success("Successfully signed in with Google")
                    },
                    onFailure = {
                        Log.e(TAG, "Google Sign In failed", it)
                        _authState.value = AuthState.Error(it.message ?: "Google sign-in failed")
                    }
                )
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error during Google Sign In", e)
                _authState.value = AuthState.Error("Unexpected error during Google sign-in")
            }
        }
    }

    fun signInWithEmailPassword(username: String, password: String) {
        Log.d(TAG, "Attempting sign in for user: $username")
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                repository.loginUser(username, password).fold(
                    onSuccess = {
                        Log.d(TAG, "Sign in successful")
                        _authState.value = AuthState.Success("Successfully signed in")
                    },
                    onFailure = {
                        Log.e(TAG, "Sign in failed", it)
                        _authState.value = AuthState.Error(it.message ?: "Sign-in failed")
                    }
                )
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error during sign in", e)
                _authState.value = AuthState.Error("Unexpected error during sign-in")
            }
        }
    }

    fun register(username: String, password: String, dateOfBirth: String? = null) {
        Log.d(TAG, "Attempting registration for user: $username")
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                repository.registerUser(username, password, dateOfBirth).fold(
                    onSuccess = {
                        Log.d(TAG, "Registration successful")
                        _authState.value = AuthState.Success("Successfully registered")
                    },
                    onFailure = {
                        Log.e(TAG, "Registration failed", it)
                        _authState.value = AuthState.Error(it.message ?: "Registration failed")
                    }
                )
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error during registration", e)
                _authState.value = AuthState.Error("Unexpected error during registration")
            }
        }
    }

    fun signOut() {
        Log.d(TAG, "Signing out")
        _authState.value = AuthState.Initial
    }

    fun isUserSignedIn(): Boolean {
        return _authState.value is AuthState.Success
    }

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                    val database = AppDatabase.getDatabase(context)
                    val repository = UserRepository(database.userDao())
                    return AuthViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}