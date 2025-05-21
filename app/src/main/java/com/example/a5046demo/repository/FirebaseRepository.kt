package com.example.a5046demo.repository

import com.example.a5046demo.data.UserProfile
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.a5046demo.model.DailyStat
import kotlinx.coroutines.tasks.await

class FirebaseRepository {
    private val db = Firebase.firestore

    suspend fun logExercise(
        uid: String,
        date: String,
        duration: Int,
        calories: Int,
        intensityIndex: Int
    ) {
        val docRef = db.collection("users")
            .document(uid)
            .collection("dailyStats")
            .document(date)

        val snapshot = docRef.get().await()
        val existing = snapshot.toObject(DailyStat::class.java)

        val updated = DailyStat(
            date = date,
            weight = existing?.weight ?: 0f,
            totalCaloriesBurned = (existing?.totalCaloriesBurned ?: 0) + calories,
            totalExerciseTime = (existing?.totalExerciseTime ?: 0) + duration,
            exerciseCounts = (existing?.exerciseCounts ?: listOf(0, 0, 0)).toMutableList().apply {
                this[intensityIndex] = this[intensityIndex] + 1
            }
        )

        docRef.set(updated).await()
    }

    suspend fun getUserProfile(uid: String): UserProfile? {
        return try {
            val userProfileSnapshot = db.collection("users").document(uid).get().await()
            if (userProfileSnapshot.exists()) {
                userProfileSnapshot.toObject(UserProfile::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }

    }
}