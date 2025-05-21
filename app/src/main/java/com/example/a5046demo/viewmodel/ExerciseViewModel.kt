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


class ExerciseViewModel(application: Application, userId: String) : AndroidViewModel(application) {

    private val repository = ExerciseRepository(application)
    val allRecords: Flow<List<ExerciseRecord>> = repository.getRecordsByUser(userId)

    fun insertRecord(record: ExerciseRecord) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(record)
    }

    fun updateRecord(record: ExerciseRecord) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(record)
    }

    fun deleteRecord(record: ExerciseRecord) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(record)
    }


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

}
