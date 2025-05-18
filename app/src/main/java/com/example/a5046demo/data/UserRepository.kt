package com.example.a5046demo.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

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

    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }
}