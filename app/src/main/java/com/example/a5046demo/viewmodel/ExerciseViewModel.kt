package com.example.a5046demo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a5046demo.data.ExerciseRecord
import com.example.a5046demo.repository.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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
}