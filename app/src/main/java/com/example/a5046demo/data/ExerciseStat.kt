package com.example.a5046demo.data

import androidx.compose.ui.graphics.Color

/**
 * Data class representing a summarized statistic for a specific type of exercise.
 * Typically used for UI visualizations such as pie charts or bar graphs.
 *
 * @param name The name of the exercise category (e.g., "Running", "Cycling").
 * @param calories The total calories burned for this exercise type.
 * @param color The color associated with this exercise type in visual charts.
 */
data class ExerciseStat(
    val name: String,
    val calories: Int,
    val color: Color
)
