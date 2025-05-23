package com.example.a5046demo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a single exercise record in the FitNest app.
 * Each record stores details about one workout session performed by a user.
 *
 * This entity is stored in the "exercise_record" table within the Room database.
 */
@Entity(tableName = "exercise_record")
data class ExerciseRecord(
    /**
     * Auto-generated unique ID for the exercise record (primary key).
     */
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    /**
     * Type of exercise performed (e.g., "Running", "Yoga", "Cycling").
     */
    val exerciseType: String,

    /**
     * Date of the exercise in string format (e.g., "22/05/2025").
     */
    val date: String,

    /**
     * Duration of the exercise in minutes.
     */
    val duration: Int,

    /**
     * Intensity level of the workout (e.g., "Low", "Medium", "High").
     */
    val intensity: String,

    /**
     * The user ID (usually Firebase UID) that this record belongs to.
     * Acts as a foreign key reference to identify the exercise owner.
     */
    val userId: String
)
