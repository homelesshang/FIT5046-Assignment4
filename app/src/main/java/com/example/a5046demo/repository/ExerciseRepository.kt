package com.example.a5046demo.repository

import android.app.Application
import com.example.a5046demo.data.AppDatabase
import com.example.a5046demo.data.ExerciseRecord
import kotlinx.coroutines.flow.Flow

/**
 * Repository class that provides an abstraction layer over the ExerciseRecord DAO.
 * Handles exercise-related data operations and communicates with the Room database.
 *
 * This class is initialized with an Android Application context to obtain a singleton instance
 * of the AppDatabase.
 */
class ExerciseRepository(application: Application) {

    // Reference to the DAO for exercise_record table
    private val dao = AppDatabase.getDatabase(application).exerciseRecordDao()

    /**
     * Returns a flow of exercise records for the given user ID, ordered by date descending.
     * Suitable for use with Compose or LiveData to observe real-time changes.
     *
     * @param userId The ID of the user whose records should be retrieved.
     * @return A Flow emitting a list of ExerciseRecord objects.
     */
    fun getRecordsByUser(userId: String): Flow<List<ExerciseRecord>> {
        return dao.getRecordsByUser(userId)
    }

    /**
     * Inserts a new exercise record into the database.
     *
     * @param record The exercise record to insert.
     */
    suspend fun insert(record: ExerciseRecord) = dao.insertRecord(record)

    /**
     * Updates an existing exercise record in the database.
     *
     * @param record The updated record.
     */
    suspend fun update(record: ExerciseRecord) = dao.updateRecord(record)

    /**
     * Deletes an exercise record from the database.
     *
     * @param record The record to delete.
     */
    suspend fun delete(record: ExerciseRecord) = dao.deleteRecord(record)
}
