package com.example.a5046demo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseRecordDao {

    @Query("SELECT * FROM exercise_record WHERE userId = :userId ORDER BY date DESC")
    fun getRecordsByUser(userId: String): Flow<List<ExerciseRecord>>

    @Insert
    suspend fun insertRecord(record: ExerciseRecord)

    @Update
    suspend fun updateRecord(record: ExerciseRecord)

    @Delete
    suspend fun deleteRecord(record: ExerciseRecord)
}