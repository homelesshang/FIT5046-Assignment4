package com.example.a5046demo.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Repository class that provides an abstraction layer between the data source (Room database)
 * and the view layer. Handles user-related operations including registration, login,
 * and Google account integration.
 *
 * Executes all database operations on the IO dispatcher to avoid blocking the main thread.
 */
class UserRepository(private val userDao: UserDao) {

    /**
     * Registers a new user if the username does not already exist.
     *
     * @param username The unique username (or email) to register.
     * @param password The user's password.
     * @param dateOfBirth Optional birthdate (can be null).
     * @return A Result object indicating success or failure (e.g., username already exists).
     */
    suspend fun registerUser(username: String, password: String, dateOfBirth: String? = null): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val existingUser = userDao.getUserByUsername(username)
                if (existingUser != null) {
                    Result.failure(Exception("Username already exists"))
                } else {
                    userDao.insertUser(User(username, password, dateOfBirth))
                    Result.success(Unit)
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Logs in a user using username and password.
     *
     * @param username The username.
     * @param password The password.
     * @return A Result containing the User on success, or an error on failure.
     */
    suspend fun loginUser(username: String, password: String): Result<User> {
        return withContext(Dispatchers.IO) {
            try {
                val user = userDao.getUserByCredentials(username, password)
                if (user != null) {
                    Result.success(user)
                } else {
                    Result.failure(Exception("Invalid username or password"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Logs in or creates a user using Google account email.
     * If the user doesn't exist, it creates a new one marked as a Google user.
     *
     * @param email The email associated with the Google account.
     * @return A Result containing the User on success, or an error on failure.
     */
    suspend fun loginWithGoogle(email: String): Result<User> {
        return withContext(Dispatchers.IO) {
            try {
                // Check if user exists
                var user = userDao.getGoogleUser(email)
                if (user == null) {
                    // Create new Google user
                    user = User(email, "google-auth", isGoogleUser = true)
                    userDao.insertUser(user)
                }
                Result.success(user)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Retrieves a Flow stream of all users in the database.
     * Useful for observing changes in the user list in real-time (e.g., admin dashboard).
     *
     * @return A Flow emitting a list of all users.
     */
    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }
}
