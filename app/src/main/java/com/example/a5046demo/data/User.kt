package com.example.a5046demo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a user account in the FitNest app.
 * This table stores both regular and Google-authenticated users.
 *
 * Stored in the "users" table of the Room database.
 */
@Entity(tableName = "users")
data class User(
    /**
     * The unique username of the user (serves as the primary key).
     * For Google users, this may be their email address.
     */
    @PrimaryKey
    val username: String,

    /**
     * The user's password in plain text or hashed format.
     * For Google users, this field may be empty or ignored.
     */
    val password: String,

    /**
     * Optional date of birth for the user, stored as a string (e.g., "01/01/2000").
     */
    val dateOfBirth: String? = null,

    /**
     * Indicates whether the user signed up via Google Sign-In.
     */
    val isGoogleUser: Boolean = false
)
