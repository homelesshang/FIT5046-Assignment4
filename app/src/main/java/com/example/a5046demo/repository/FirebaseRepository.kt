package com.example.a5046demo.repository

import android.util.Log
import com.example.a5046demo.data.UserProfile
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.a5046demo.model.DailyStat
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Repository class that handles interaction with Firebase Firestore.
 * Manages user profile data and daily exercise statistics stored remotely.
 */
class FirebaseRepository {

    // Reference to the Firestore database
    private val db = Firebase.firestore

    /**
     * Logs an exercise session into Firestore under the current user's daily statistics.
     * If a record for the given date already exists, the data is incrementally updated.
     *
     * @param uid User ID (usually Firebase UID).
     * @param date Date of the exercise session (e.g., "2025-05-23").
     * @param duration Duration of the exercise in minutes.
     * @param calories Calories burned during the session.
     * @param intensityIndex Index indicating exercise intensity: 0 = low, 1 = medium, 2 = high.
     */
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

    /**
     * Retrieves a user's profile from Firestore.
     *
     * @param uid The user ID.
     * @return The corresponding UserProfile if found, or null if not available.
     */
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

    /**
     * Updates the user's weight in both the profile and the daily log for a specific date.
     *
     * @param userId Firebase UID of the user.
     * @param date The date to associate with the new weight value.
     * @param weight The user's weight in kilograms.
     */
    suspend fun updateWeight(userId: String, date: String, weight: Float) {
        val db = FirebaseFirestore.getInstance()

        // 1. Update weight in user profile
        db.collection("users").document(userId)
            .update("weight", weight)

        // 2. Upsert weight into the corresponding dailyStat document
        val docRef = db.collection("users")
            .document(userId)
            .collection("dailyStats")
            .document(date)

        val snapshot = docRef.get().await()
        val existing = snapshot.toObject(DailyStat::class.java)

        val updated = DailyStat(
            date = date,
            weight = weight,
            totalCaloriesBurned = existing?.totalCaloriesBurned ?: 0,
            totalExerciseTime = existing?.totalExerciseTime ?: 0,
            exerciseCounts = existing?.exerciseCounts ?: listOf(0, 0, 0)
        )

        docRef.set(updated).await()
    }
}
