package com.example.a5046demo.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    var userId: String = "",

    var nickname: String = "",

    var weight: Float? = null,

    var height: Float? = null,

    var birthday: String? = null
)