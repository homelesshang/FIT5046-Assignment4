package com.example.a5046demo.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val userId: String,   // 使用 Firebase UID 作为主键，唯一标识用户
    val nickname: String,
    val weight: Float,
    val height: Float
)