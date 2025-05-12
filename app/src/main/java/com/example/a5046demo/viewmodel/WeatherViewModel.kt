package com.example.a5046demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a5046demo.network.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _weather = MutableStateFlow("Loading...")
    val weather: StateFlow<String> = _weather

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            try {
                val response = repository.getWeather(city)
                val weatherText = "${response.name}: ${response.main.temp}°C, ${response.weather[0].description}"
                _weather.value = weatherText
            } catch (e: Exception) {
                _weather.value = "Failed to fetch weather"
            }
        }
    }
}