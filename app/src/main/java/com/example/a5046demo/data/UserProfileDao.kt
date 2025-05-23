package com.example.a5046demo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for interacting with the user_profile table.
 * Manages reading and writing of user profile information such as nickname, height, weight, etc.
 */
@Dao
interface UserProfileDao {

    /**
     * Retrieves the user profile for a given userId as a Flow.
     * This allows the UI to automatically update when the profile data changes.
     *
     * @param userId The unique identifier of the user.
     * @return A Flow emitting the UserProfile or null if not found.
     */
    @Query("SELECT * FROM user_profile WHERE userId = :userId LIMIT 1")
    fun getUserProfile(userId: String): Flow<UserProfile?>

    /**
     * Retrieves the user profile once (non-observable).
     * Useful for one-time queries such as form pre-filling or API syncing.
     *
     * @param userId The unique identifier of the user.
     * @return The UserProfile object, or null if not found.
     */
    @Query("SELECT * FROM user_profile WHERE userId = :userId LIMIT 1")
    suspend fun getUserProfileOnce(userId: String): UserProfile?

    /**
     * Inserts or replaces a user profile in the database.
     * If a profile with the same userId exists, it will be overwritten.
     *
     * @param profile The UserProfile to insert or update.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile: UserProfile)

    /**
     * Updates an existing user profile.
     * Will only succeed if a matching profile (by primary key) already exists.
     *
     * @param profile The modified UserProfile object.
     */
    @Update
    suspend fun update(profile: UserProfile)

    /**
     * Deletes a user profile from the database.
     *
     * @param profile The profile to remove.
     */
    @Delete
    suspend fun delete(profile: UserProfile)
}
