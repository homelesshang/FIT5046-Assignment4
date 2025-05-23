package com.example.a5046demo.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Defines possible authentication states for the UI to observe
sealed class AuthState {
    object Initial : AuthState()                   // Initial state, no authentication action yet
    object Loading : AuthState()                   // Authentication in progress
    data class Success(val message: String) : AuthState()  // Auth success with user info
    data class Error(val message: String) : AuthState()    // Auth failure with error message
}

class AuthViewModel : ViewModel() {
    // Backing state for authentication status
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState

    /**
     * Creates and returns a GoogleSignInClient configured with necessary scopes.
     */
    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestId()
            .requestIdToken("542302828277-ke8g17kogv1li2s6jtr8dd67f6snink9.apps.googleusercontent.com") // Replace with your client ID
            .build()

        return GoogleSignIn.getClient(context, gso)
    }

    /**
     * Signs in the user using a Google ID token.
     */
    fun signInWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        _authState.value = AuthState.Loading

        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    _authState.value = AuthState.Success(user?.uid ?: "Google Sign-in success")
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Google sign-in failed")
                }
            }
    }

    /**
     * Signs in the user using an email and password.
     */
    fun signInWithEmailPassword(email: String, password: String) {
        _authState.value = AuthState.Loading

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    _authState.value = AuthState.Success(user?.uid ?: "Signed in")
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Sign-in failed")
                }
            }
    }

    /**
     * Registers a new user with email and password, and stores initial profile data in Firestore.
     */
    fun register(email: String, password: String, dateOfBirth: String) {
        _authState.value = AuthState.Loading

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    val uid = user?.uid

                    if (uid != null) {
                        // Derive nickname from email before @
                        val nickname = email.substringBefore("@")

                        // Initial profile setup for Firestore
                        val userData = mapOf(
                            "userId" to uid,
                            "email" to email,
                            "nickname" to nickname,
                            "dateOfBirth" to dateOfBirth,
                            "region" to "",
                            "height" to null,
                            "weight" to null
                        )

                        Firebase.firestore.collection("users").document(uid)
                            .set(userData)
                            .addOnSuccessListener {
                                _authState.value = AuthState.Success(uid)
                            }
                            .addOnFailureListener { e ->
                                _authState.value = AuthState.Error("Profile save failed: ${e.message}")
                            }
                    } else {
                        _authState.value = AuthState.Error("Missing user ID")
                    }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Registration failed")
                }
            }
    }

    /**
     * Signs the user out and resets the authentication state.
     */
    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        _authState.value = AuthState.Initial
        Log.d(TAG, "Firebase sign-out successful")
    }

    /**
     * Checks if the user is currently signed in.
     */
    fun isUserSignedIn(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }
}
