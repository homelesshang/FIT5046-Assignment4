package com.example.a5046demo.viewmodel

import android.content.ContentValues.TAG
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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



sealed class AuthState {
    object Initial : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState


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

    fun register(email: String, password: String, dateOfBirth: String) {
        _authState.value = AuthState.Loading
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    val uid = user?.uid

                    if (uid != null) {
                        Firebase.firestore.collection("users").document(uid)
                            .set(mapOf("email" to email, "dateOfBirth" to dateOfBirth))
                    }
                    _authState.value = AuthState.Success(uid ?: "Registered")
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Registration failed")
                }
            }
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        _authState.value = AuthState.Initial
        Log.d(TAG, "Firebase sign-out successful")
    }

    fun isUserSignedIn(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }


}