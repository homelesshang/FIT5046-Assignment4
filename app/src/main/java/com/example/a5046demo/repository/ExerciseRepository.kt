package com.example.a5046demo.repository

import android.app.Application
import com.example.a5046demo.data.AppDatabase
import com.example.a5046demo.data.ExerciseRecord
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(application: Application) {

    private val dao = AppDatabase.getDatabase(application).exerciseRecordDao()


    fun getRecordsByUser(userId: String): Flow<List<ExerciseRecord>> {
        return dao.getRecordsByUser(userId)
    }

    suspend fun insert(record: ExerciseRecord) = dao.insertRecord(record)
    suspend fun update(record: ExerciseRecord) = dao.updateRecord(record)
    suspend fun delete(record: ExerciseRecord) = dao.deleteRecord(record)
}