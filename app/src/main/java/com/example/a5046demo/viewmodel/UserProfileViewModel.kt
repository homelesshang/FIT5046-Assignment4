package com.example.a5046demo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a5046demo.data.AppDatabase
import com.example.a5046demo.data.UserProfile
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserProfileViewModel(application: Application, userId: String) : AndroidViewModel(application) {

    private val userProfileDao = AppDatabase.getDatabase(application).userProfileDao()

    val userProfile: Flow<UserProfile?> = userProfileDao.getUserProfile(userId)

    fun updateProfile(profile: UserProfile) = viewModelScope.launch(Dispatchers.IO) {
        userProfileDao.insertOrUpdate(profile)

        Firebase.firestore.collection("users")
            .document(profile.userId)
            .set(profile)

    }
}