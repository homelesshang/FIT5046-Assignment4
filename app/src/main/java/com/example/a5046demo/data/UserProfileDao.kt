package com.example.a5046demo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface UserProfileDao {

    @Query("SELECT * FROM user_profile WHERE userId = :userId LIMIT 1")
    fun getUserProfile(userId: String): Flow<UserProfile?>


    @Query("SELECT * FROM user_profile WHERE userId = :userId LIMIT 1")
    suspend fun getUserProfileOnce(userId: String): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile: UserProfile)

    @Update
    suspend fun update(profile: UserProfile)

    @Delete
    suspend fun delete(profile: UserProfile)
}