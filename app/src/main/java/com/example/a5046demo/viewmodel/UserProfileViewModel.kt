package com.example.a5046demo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a5046demo.data.AppDatabase
import com.example.a5046demo.data.UserProfile
import com.example.a5046demo.repository.FirebaseRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel for managing user profile data from both local database and Firebase
class UserProfileViewModel(application: Application, private val userId: String) : AndroidViewModel(application) {

    // Local Room database DAO
    private val userProfileDao = AppDatabase.getDatabase(application).userProfileDao()
    // Repository to interact with Firebase
    private val firebaseRepository = FirebaseRepository()

    // Flow from Room database for observing profile changes
    val userProfileFromDao: Flow<UserProfile?> = userProfileDao.getUserProfile(userId)

    // Mutable state to hold current user profile in memory
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile

    // Insert or update profile in local database
    fun insertLocalProfile(profile: UserProfile) = viewModelScope.launch(Dispatchers.IO) {
        userProfileDao.insertOrUpdate(profile)
    }

    // Update profile in both local Room database and remote Firebase Firestore
    fun updateProfile(profile: UserProfile) = viewModelScope.launch(Dispatchers.IO) {
        userProfileDao.insertOrUpdate(profile)

        // Save updated profile to Firebase Firestore
        Firebase.firestore.collection("users")
            .document(profile.userId)
            .set(profile)
    }

    // Sync user profile from Firebase to local Room database
    suspend fun syncUserProfileFromFirebase(): Boolean {
        val firebaseProfile = firebaseRepository.getUserProfile(userId)

        return if (firebaseProfile != null) {
            userProfileDao.insertOrUpdate(firebaseProfile)
            Log.d("SyncProfile", "Synced user profile from Firebase for userId: $userId")
            true
        } else {
            Log.w("SyncProfile", "No profile found in Firebase for userId: $userId")
            false
        }
    }

    // Update only the weight field in the in-memory profile state
    fun updateLocalWeight(newWeight: Float) {
        val current = _userProfile.value
        if (current != null) {
            _userProfile.value = current.copy(weight = newWeight)
        }
    }

    // Collect local profile changes and update StateFlow
    init {
        viewModelScope.launch {
            userProfileFromDao.collect {
                _userProfile.value = it
            }
        }
    }

}
