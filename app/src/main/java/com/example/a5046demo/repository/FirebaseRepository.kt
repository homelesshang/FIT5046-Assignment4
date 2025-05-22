package com.example.a5046demo.repository

import android.util.Log
import com.example.a5046demo.data.UserProfile
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.a5046demo.model.DailyStat
import kotlinx.coroutines.tasks.await
import com.example.a5046demo.repository.FirebaseRepository
import com.google.firebase.firestore.FirebaseFirestore


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
            val snapshot = db.collection("users").document(uid).get().await()
            if (snapshot.exists()) {
                Log.d("FirebaseRepo", "Fetched user profile from Firebase for $uid")
                snapshot.toObject(UserProfile::class.java)
            } else {
                Log.w("FirebaseRepo", "No profile exists in Firebase for $uid")
                null
            }
        } catch (e: Exception) {
            Log.e("FirebaseRepo", "Failed to fetch user profile: ${e.message}")
            null
        }
    }

    suspend fun updateWeight(userId: String, date: String, weight: Float) {
        val db = FirebaseFirestore.getInstance()

        // 1. Update user profile weight
        db.collection("users").document(userId)
            .update("weight", weight)

        // 2. Upsert daily stat with weight
        val docRef = db.collection("users")
            .document(userId)
            .collection("dailyStats")
            .document(date)

        val snapshot = docRef.get().await()
        val existing = snapshot.toObject(DailyStat::class.java)

        val updated = DailyStat(
            date = date,
            weight = weight, // <-- this overwrites any previous weight
            totalCaloriesBurned = existing?.totalCaloriesBurned ?: 0,
            totalExerciseTime = existing?.totalExerciseTime ?: 0,
            exerciseCounts = existing?.exerciseCounts ?: listOf(0, 0, 0)
        )

        docRef.set(updated).await()
    }

}