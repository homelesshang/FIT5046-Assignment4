package com.example.a5046demo.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Factory class used to instantiate ExerciseViewModel with required parameters
class ExerciseViewModelFactory(
    private val application: Application, // Android application context
    private val userId: String            // Firebase user ID
) : ViewModelProvider.Factory {

    // Generic method to create a ViewModel instance
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the requested ViewModel is of type ExerciseViewModel
        if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
            // Return a new instance of ExerciseViewModel with required parameters
            return ExerciseViewModel(application, userId) as T
        }
        // Throw an exception if the ViewModel class is not recognized
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
