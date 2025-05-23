package com.example.a5046demo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing additional user profile information in the FitNest app.
 * This data is stored in the "user_profile" table and complements basic user credentials.
 *
 * Each user has exactly one UserProfile, linked by the userId (Firebase UID or local username).
 */
@Entity(tableName = "user_profile")
data class UserProfile(
    /**
     * Unique user identifier (acts as primary key).
     * Typically corresponds to the Firebase UID or local username.
     */
    @PrimaryKey
    var userId: String = "",

    /**
     * User's display name or nickname shown in the UI.
     */
    var nickname: String = "",

    /**
     * Optional user weight in kilograms.
     */
    var weight: Float? = null,

    /**
     * Optional user height in centimeters.
     */
    var height: Float? = null,

    /**
     * Optional user birthday in string format (e.g., "2000-01-01").
     */
    var birthday: String? = null,

    /**
     * Optional user region or location (e.g., country or city).
     */
    var region: String? = null
)
