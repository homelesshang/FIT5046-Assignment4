package com.example.a5046demo.model

data class DailyStat(
    val date: String = "",
                     val weight: Float = 0f,
                     val totalCaloriesBurned: Int = 0,
                     val totalExerciseTime: Int = 0,
                     val exerciseCounts: List<Int> = listOf(0, 0, 0) // [low, medium, high]
)
