package com.example.a5046demo.model

/**
 * Data class representing a summary of a user's fitness activity on a single day.
 * Used for displaying daily statistics such as calories burned, total duration, and intensity distribution.
 *
 * Typically used to populate dashboards, charts, or historical progress tracking.
 *
 * @param date The date this record refers to (e.g., "2025-05-22").
 * @param weight The user's weight on this date, if recorded (in kilograms).
 * @param totalCaloriesBurned Total calories burned across all exercises on this day.
 * @param totalExerciseTime Total time spent exercising on this day (in minutes).
 * @param exerciseCounts A list representing the count of exercises by intensity level:
 *        [0] = low intensity, [1] = medium intensity, [2] = high intensity.
 */
data class DailyStat(
    val date: String = "",
    val weight: Float = 0f,
    val totalCaloriesBurned: Int = 0,
    val totalExerciseTime: Int = 0,
    val exerciseCounts: List<Int> = listOf(0, 0, 0) // [low, medium, high]
)
