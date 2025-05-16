package com.fit5046.fitnest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val username: String,
    val password: String,
    val dateOfBirth: String? = null,
    val isGoogleUser: Boolean = false
) 