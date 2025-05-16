package com.example.a5046demo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseRecordDao {

    @Query("SELECT * FROM exercise_record ORDER BY date DESC")
    fun getAllRecords(): Flow<List<ExerciseRecord>>

    @Insert
    suspend fun insertRecord(record: ExerciseRecord)

    @Update
    suspend fun updateRecord(record: ExerciseRecord)

    @Delete
    suspend fun deleteRecord(record: ExerciseRecord)
}