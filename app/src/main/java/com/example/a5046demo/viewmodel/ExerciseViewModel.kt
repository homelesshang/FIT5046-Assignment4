package com.example.a5046demo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a5046demo.data.ExerciseRecord
import com.example.a5046demo.repository.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.example.a5046demo.repository.FirebaseRepository
import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.a5046demo.data.ExerciseStat
import com.example.a5046demo.model.DailyStat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class ExerciseViewModel(application: Application, userId: String) : AndroidViewModel(application) {

    // Estimated calorie burn rate by exercise type
    private val calorieRates = mapOf(
        "Cardio" to 6,
        "Strength" to 8,
        "Yoga" to 3,
        "HIIT" to 10,
        "Pilates" to 4
    )

    // Holds current day's exercise stats grouped by type
    private val _todayStats = MutableStateFlow<List<ExerciseStat>>(emptyList())
    val todayStats: StateFlow<List<ExerciseStat>> = _todayStats

    // Holds weekly statistics (weight, calories, duration) mapped to weekday labels
    private val _weeklyStats = MutableStateFlow<List<LabeledStat>>(emptyList())
    val weeklyStats: StateFlow<List<LabeledStat>> = _weeklyStats

    // Repository for accessing local Room database
    private val repository = ExerciseRepository(application)

    // Live stream of all records for this user
    val allRecords: Flow<List<ExerciseRecord>> = repository.getRecordsByUser(userId)

    // Insert a new record to the local database
    fun insertRecord(record: ExerciseRecord) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(record)
    }

    // Update an existing record
    fun updateRecord(record: ExerciseRecord) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(record)
    }

    // Delete a record
    fun deleteRecord(record: ExerciseRecord) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(record)
    }

    // Log exercise data to Firebase (for syncing with cloud)
    fun logExerciseToFirebase(
        uid: String,
        date: String,
        duration: Int,
        calories: Int,
        intensityIndex: Int
    ) {
        val repo = FirebaseRepository()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.logExercise(uid, date, duration, calories, intensityIndex)
            } catch (e: Exception) {
                Log.e("ExerciseViewModel", "Firebase log failed: ${e.message}")
            }
        }
    }

    // Load today’s stats from local database and compute grouped calorie values
    fun loadTodayStats() {
        viewModelScope.launch {
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val all = allRecords.firstOrNull() ?: emptyList()

            val todayRecords = all.filter { it.date == today }
            val grouped = todayRecords.groupBy { it.exerciseType }

            val stats = grouped.map { (type, records) ->
                val totalDuration = records.sumOf { it.duration }
                val calories = totalDuration * (calorieRates[type] ?: 5)
                ExerciseStat(name = type, calories = calories, color = getColorForType(type))
            }

            _todayStats.value = stats
        }
    }

    // Return a color for each exercise type for UI purposes
    private fun getColorForType(type: String): Color = when (type) {
        "Cardio" -> Color(0xFF2E8B57)
        "Strength" -> Color(0xFF000000)
        "Yoga" -> Color(0xFF8E44AD)
        "HIIT" -> Color(0xFFE74C3C)
        "Pilates" -> Color(0xFF888888)
        else -> Color.Transparent
    }

    // For future use: holds raw list of daily stats from Firebase
    val dailyStats = MutableStateFlow<List<DailyStat>>(emptyList())

    // Wrapper data class for labeled stats shown in graphs
    data class LabeledStat(
        val label: String, // E.g., "Mon", "Tue"
        val stat: DailyStat
    )

    // Load the last 7 days’ stats from Firebase and attach weekday labels
    fun loadWeeklyStats(uid: String) {
        viewModelScope.launch {
            val labeledStats = mutableListOf<LabeledStat>()
            val db = Firebase.firestore
            val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
            val dayLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

            // Find most recent Monday
            val today = LocalDate.now()
            val monday = today.minusDays((today.dayOfWeek.value % 7).toLong())

            for (i in 0..6) {
                val date = monday.plusDays(i.toLong())
                val docId = date.format(formatter)

                val snapshot = db.collection("users")
                    .document(uid)
                    .collection("dailyStats")
                    .document(docId)
                    .get()
                    .await()

                val stat = snapshot.toObject(DailyStat::class.java) ?: DailyStat(date = docId)
                labeledStats.add(LabeledStat(label = dayLabels[i], stat = stat))
            }

            _weeklyStats.value = labeledStats
        }
    }
}
