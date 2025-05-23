package com.example.a5046demo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for accessing user data in the "users" table.
 * Provides CRUD operations for both regular and Google-authenticated users.
 */
@Dao
interface UserDao {

    /**
     * Inserts a new user into the database.
     *
     * @param user The user to insert.
     * If the username already exists, insertion will fail due to OnConflictStrategy.ABORT.
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    /**
     * Retrieves a user by username.
     *
     * @param username The username to query.
     * @return The corresponding User object, or null if not found.
     */
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    /**
     * Authenticates a user using both username and password.
     *
     * @param username The username.
     * @param password The password.
     * @return The User object if credentials match, or null otherwise.
     */
    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getUserByCredentials(username: String, password: String): User?

    /**
     * Retrieves a Google-authenticated user by username (typically an email).
     *
     * @param username The Google user's email.
     * @return The User object if found and isGoogleUser is true, or null.
     */
    @Query("SELECT * FROM users WHERE username = :username AND isGoogleUser = 1 LIMIT 1")
    suspend fun getGoogleUser(username: String): User?

    /**
     * Retrieves all users in the database.
     *
     * @return A Flow that emits the list of users whenever the data changes.
     */
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    /**
     * Deletes a user from the database.
     *
     * @param user The user to delete.
     */
    @Delete
    suspend fun deleteUser(user: User)

    /**
     * Updates an existing user in the database.
     *
     * @param user The updated user information.
     */
    @Update
    suspend fun updateUser(user: User)
}
