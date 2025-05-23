package com.example.a5046demo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Main Room database class for the FitNest app.
 * It includes tables for User accounts, exercise records, and user profiles.
 *
 * @Database annotation defines:
 * - entities: List of data models (tables) managed by this database.
 * - version: Schema version, must be incremented on structural changes.
 * - exportSchema: Whether to export schema info for tooling (disabled here).
 */
@Database(
    entities = [User::class, ExerciseRecord::class, UserProfile::class],
    version = 8,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Provides access to user table operations.
     */
    abstract fun userDao(): UserDao

    /**
     * Provides access to exercise record table operations.
     */
    abstract fun exerciseRecordDao(): ExerciseRecordDao

    /**
     * Provides access to user profile table operations.
     */
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        // Volatile ensures visibility of changes across threads.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Singleton pattern to ensure only one instance of the database is created.
         *
         * @param context Application context used to initialize Room.
         * @return Singleton instance of AppDatabase.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fitnest_database" // The name of the underlying SQLite database file.
                )
                    // Destroys and recreates the database if no migration is provided.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
