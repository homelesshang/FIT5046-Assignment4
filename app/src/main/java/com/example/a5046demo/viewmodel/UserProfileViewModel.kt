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
import kotlinx.coroutines.launch

class UserProfileViewModel(application: Application, private val userId: String) : AndroidViewModel(application) {

    private val userProfileDao = AppDatabase.getDatabase(application).userProfileDao()
    private val firebaseRepository = FirebaseRepository()

    val userProfile: Flow<UserProfile?> = userProfileDao.getUserProfile(userId)

    fun insertLocalProfile(profile: UserProfile) = viewModelScope.launch(Dispatchers.IO) {
        userProfileDao.insertOrUpdate(profile)
    }

    fun updateProfile(profile: UserProfile) = viewModelScope.launch(Dispatchers.IO) {
        userProfileDao.insertOrUpdate(profile)

        Firebase.firestore.collection("users")
            .document(profile.userId)
            .set(profile)

    }

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
}