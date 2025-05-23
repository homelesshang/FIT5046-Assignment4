package com.example.a5046demo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for interacting with the exercise_record table.
 * Provides methods to insert, update, delete, and query exercise records.
 */
@Dao
interface ExerciseRecordDao {

    /**
     * Retrieves all exercise records for a specific user, ordered by date (latest first).
     *
     * @param userId The ID of the user whose records are being queried.
     * @return A Flow emitting the list of exercise records in descending date order.
     */
    @Query("SELECT * FROM exercise_record WHERE userId = :userId ORDER BY date DESC")
    fun getRecordsByUser(userId: String): Flow<List<ExerciseRecord>>

    /**
     * Inserts a new exercise record into the database.
     *
     * @param record The ExerciseRecord object to insert.
     */
    @Insert
    suspend fun insertRecord(record: ExerciseRecord)

    /**
     * Updates an existing exercise record in the database.
     *
     * @param record The updated ExerciseRecord object.
     */
    @Update
    suspend fun updateRecord(record: ExerciseRecord)

    /**
     * Deletes an exercise record from the database.
     *
     * @param record The ExerciseRecord object to delete.
     */
    @Delete
    suspend fun deleteRecord(record: ExerciseRecord)
}
