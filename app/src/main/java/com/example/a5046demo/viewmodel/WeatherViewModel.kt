package com.example.a5046demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a5046demo.network.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel responsible for managing weather data
class WeatherViewModel : ViewModel() {

    // Instance of the repository that handles network requests
    private val repository = WeatherRepository()

    // Mutable state to hold the weather text shown in UI
    private val _weather = MutableStateFlow("Loading...")
    val weather: StateFlow<String> = _weather  // Exposed read-only state for observers

    // Function to fetch weather data for a given city
    fun fetchWeather(city: String) {
        viewModelScope.launch {
            try {
                // Make network call to get weather information
                val response = repository.getWeather(city)
                // Format the response into a readable string
                val weatherText = "${response.name}: ${response.main.temp}°C, ${response.weather[0].description}"
                _weather.value = weatherText  // Update the state to be displayed in UI
            } catch (e: Exception) {
                // If network call fails, update state with an error message
                _weather.value = "Failed to fetch weather"
            }
        }
    }
}
