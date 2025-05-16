package com.example.a5046demo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_record")
data class ExerciseRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val exerciseType: String,
    val date: String,
    val duration: Int,
    val intensity: String
)